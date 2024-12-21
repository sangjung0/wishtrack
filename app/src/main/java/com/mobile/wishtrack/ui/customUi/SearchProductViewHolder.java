package com.mobile.wishtrack.ui.customUi;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.github.mikephil.charting.charts.LineChart;
import com.mobile.wishtrack.R;
import com.mobile.wishtrack.domain.model.Product;
import com.mobile.wishtrack.ui.Chart;
import com.mobile.wishtrack.ui.adapter.SearchListAdapter;

import java.text.NumberFormat;
import java.util.Locale;

public class SearchProductViewHolder extends RecyclerView.ViewHolder {
    private ImageView productImage;
    private TextView productTitle;
    private TextView productPrice;
    private TextView productChangeRate;
    private ImageView productChangeRateArrow;
    private ImageView productCart;
    private LineChart productChangeRateChart;

    private SearchProduct view;

    public SearchProductViewHolder(@NonNull SearchProduct view) {
        super(view);
        this.view = view;

        this.productImage = this.view.findViewById(R.id.productImage);
        this.productTitle = this.view.findViewById(R.id.productTitle);
        this.productPrice = this.view.findViewById(R.id.productPrice);
        this.productChangeRate = this.view.findViewById(R.id.productChangeRate);
        this.productChangeRateArrow = this.view.findViewById(R.id.productChangeRateArrow);
        this.productCart = this.view.findViewById(R.id.productCart);
        this.productChangeRateChart = this.view.findViewById(R.id.productChangeRateChart);

        productChangeRateChart.setNoDataText("");
    }

    public void bind(Product product, SearchListAdapter.OnClickListener onClickListener) {
        /* setData */

        productImage.post(() -> {
            int imageViewHeight = productImage.getHeight(); // ImageView의 높이 가져오기

            Glide.with(view.getContext())
                    .load(product.getImage())
                    .override(Target.SIZE_ORIGINAL, imageViewHeight) // 원본 비율로 높이 맞춤
                    .into(productImage);
        });

        String cleanTitle = product.getTitle().replaceAll("<[^>]*>", ""); // HTML 태그 제거
        this.productTitle.setText(cleanTitle);

        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
        String formattedPrice = "₩ " + numberFormat.format(product.getLprice());
        this.productPrice.setText(formattedPrice);

        if (product.isWish()) wishWidget(product);
        else hideWishWidget();

        /* event setting */
        this.productImage.setOnClickListener(v -> onClickListener.onProductClick(product));
        this.productTitle.setOnClickListener(v -> onClickListener.onProductClick(product));
        this.productCart.setOnClickListener(v -> {
            //TODO 여기서 cart 이미지를 바꿔주는데, 이건 좀 별로라 생각 함.
            if (product.isWish()) {
                onClickListener.onDelete(product.getId());
                hideWishWidget();
            }
            else {
                onClickListener.onInsert(product, product::setId);
                wishWidget(product);
            }

            product.setWish(!product.isWish());
        });
    }

    private void wishWidget(Product product) {
        this.productCart.setImageResource(R.drawable.baseline_shopping_cart_24);

        int changeRate = (int) (product.getChangeRate() * 100);
        String formattedChangeRate = String.format(Locale.getDefault(), "%d%%", changeRate);
        this.productChangeRate.setVisibility(View.VISIBLE);
        this.productChangeRate.setText(formattedChangeRate);

        if (changeRate > 0) {
            this.productChangeRate.setTextColor(view.getResources().getColor(R.color.increase_price));
            this.productChangeRateArrow.setImageResource(R.drawable.baseline_keyboard_arrow_up_24);
            this.productChangeRateArrow.setColorFilter(view.getResources().getColor(R.color.increase_price));
        } else {
            this.productChangeRate.setTextColor(view.getResources().getColor(R.color.decrease_price));
            this.productChangeRateArrow.setImageResource(R.drawable.baseline_keyboard_arrow_down_24);
            this.productChangeRateArrow.setColorFilter(view.getResources().getColor(R.color.decrease_price));

            if (changeRate == 0) this.productChangeRateArrow.setVisibility(View.GONE);
        }

        Chart.setupLineChart(productChangeRateChart, product.getPrices());
        productChangeRateChart.setVisibility(View.VISIBLE);
    }

    private void hideWishWidget() {
        this.productCart.setImageResource(R.drawable.baseline_add_shopping_cart_24);
        this.productChangeRate.setVisibility(View.GONE);
        this.productChangeRateArrow.setVisibility(View.GONE);
        this.productChangeRateChart.setVisibility(View.GONE);
    }
}
