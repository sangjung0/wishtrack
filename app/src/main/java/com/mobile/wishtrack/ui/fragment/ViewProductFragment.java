package com.mobile.wishtrack.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.github.mikephil.charting.charts.LineChart;
import com.mobile.wishtrack.R;
import com.mobile.wishtrack.domain.model.Product;
import com.mobile.wishtrack.ui.Chart;
import com.mobile.wishtrack.ui.viewModel.ProductSearchViewModel;
import com.mobile.wishtrack.ui.viewModel.WishSearchViewModel;

import java.text.NumberFormat;
import java.util.Locale;


public class ViewProductFragment extends Fragment {
    private ImageView backButton;
    private TextView goToProductPage;
    private ImageView itemImage;
    private TextView itemTitle, itemLPrice, itemHPrice, itemMaker, itemBrand, itemCategory, itemProductID, itemProductType;
    private WishSearchViewModel wishSearchViewModel;
    private ProductSearchViewModel productSearchViewModel;
    private LineChart productChageRateChart;
    private String productPageUrl;

    public ViewProductFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        /* view model setting */
        wishSearchViewModel = new ViewModelProvider(requireActivity()).get(WishSearchViewModel.class);
        productSearchViewModel = new ViewModelProvider(requireActivity()).get(ProductSearchViewModel.class);

        /* view setting */
        View view = inflater.inflate(R.layout.fragment_view_product, container, false);

        backButton = view.findViewById(R.id.back_button);
        goToProductPage = view.findViewById(R.id.go_to_product_page);
        itemImage = view.findViewById(R.id.item_image);
        itemTitle = view.findViewById(R.id.item_title);
        itemLPrice = view.findViewById(R.id.item_l_price);
        itemHPrice = view.findViewById(R.id.item_h_price);
        itemMaker = view.findViewById(R.id.item_maker);
        itemBrand = view.findViewById(R.id.item_brand);
        itemCategory = view.findViewById(R.id.item_category);
        itemProductID = view.findViewById(R.id.item_product_id);
        itemProductType = view.findViewById(R.id.item_product_type);
        productChageRateChart = view.findViewById(R.id.product_chage_rate_chart);

        //TODO 임시 비활성화
        itemProductType.setVisibility(View.GONE);

        /* event setting */
        backButton.setOnClickListener((v) -> {
            wishSearchViewModel.setVisible(false);
            productSearchViewModel.setVisible(false);
        });

        goToProductPage.setOnClickListener((v)->{
            if (productPageUrl.startsWith("http")) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(productPageUrl));
                startActivity(intent);
            } else {
                Toast.makeText(getContext(), "Invalid URL", Toast.LENGTH_SHORT).show();
            }
        });

        wishSearchViewModel.getVisible().observe(getViewLifecycleOwner(), visible -> {
            setProduct(wishSearchViewModel.getProduct().getValue());
        });
        productSearchViewModel.getVisible().observe(getViewLifecycleOwner(), visible -> {
            setProduct(productSearchViewModel.getProduct().getValue());
        });

        return view;
    }

    private void setProduct(Product product) {
        if (product == null) return;

        productPageUrl = product.getLink();
        goToProductPage.setText(product.getMallName());

        itemImage.post(() -> {
            int imageViewHeight = itemImage.getHeight(); // ImageView의 높이 가져오기

            Glide.with(this)
                    .load(product.getImage())
                    .override(Target.SIZE_ORIGINAL, imageViewHeight) // 원본 비율로 높이 맞춤
                    .into(itemImage);
        });

        String cleanTitle = product.getTitle().replaceAll("<[^>]*>", "");
        itemTitle.setText(cleanTitle);


        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
        String formattedPrice = "₩ " + numberFormat.format(product.getLprice());
        itemLPrice.setText(formattedPrice);

        formattedPrice = "₩ " + numberFormat.format(product.getHprice());
        itemHPrice.setText(formattedPrice);

        String maker = product.getMaker();
        String brand = product.getBrand();
        String category = product.getCategory1();
        String productID = "네이버 상품 코드: " + String.valueOf(product.getProductId());

        if (maker != null && !maker.isEmpty()) {
            maker = "메이커: " + maker;
            itemMaker.setText(maker);
            itemMaker.setVisibility(View.VISIBLE);
        } else {
            itemMaker.setVisibility(View.GONE);
        }

        if (brand != null && !brand.isEmpty()) {
            brand = "브랜드: " + brand;
            itemBrand.setText(brand);
            itemBrand.setVisibility(View.VISIBLE);
        } else {
            itemBrand.setVisibility(View.GONE);
        }

        if (category != null && !category.isEmpty()) {
            category = "카테고리: " + category;
            itemCategory.setText(category);
            itemCategory.setVisibility(View.VISIBLE);
        } else {
            itemCategory.setVisibility(View.GONE);
        }

        itemProductID.setText(productID);

        Chart.setupLineChart(productChageRateChart,product.getPrices());
    }
}