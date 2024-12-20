package com.mobile.wishtrack.ui.customUi;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.mobile.wishtrack.R;
import com.mobile.wishtrack.domain.model.Product;
import com.mobile.wishtrack.ui.adapter.SearchListAdapter;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
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
        Glide.with(view.getContext()).load(product.getImage()).into(this.productImage);

        String cleanTitle = product.getTitle().replaceAll("<[^>]*>", ""); // HTML 태그 제거
        this.productTitle.setText(cleanTitle);

        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
        String formattedPrice = "₩ " + numberFormat.format(product.getLprice());
        this.productPrice.setText(formattedPrice);

        int changeRate = (int) (product.getChangeRate() * 100);
        String formattedChangeRate = String.format(Locale.getDefault(), "%d%%", changeRate);
        this.productChangeRate.setText(formattedChangeRate);
        this.productChangeRate.setTextColor(
                changeRate > 0
                        ? view.getResources().getColor(R.color.increase_price)
                        : view.getResources().getColor(R.color.decrease_price)
        );

        if (changeRate == 0) {
            this.productChangeRateArrow.setVisibility(View.GONE); // 0이면 숨김
        } else {
            this.productChangeRateArrow.setVisibility(View.VISIBLE); // 0이 아니면 보임
            this.productChangeRateArrow.setImageResource(
                    changeRate > 0 ? R.drawable.baseline_keyboard_arrow_up_24 : R.drawable.baseline_keyboard_arrow_down_24
            );
            this.productChangeRateArrow.setColorFilter(
                    changeRate > 0
                            ? view.getResources().getColor(R.color.increase_price)
                            : view.getResources().getColor(R.color.decrease_price)
            );
        }

        this.productCart.setImageResource(product.isWish() ? R.drawable.baseline_shopping_cart_24 : R.drawable.baseline_add_shopping_cart_24);

        if (product.isWish()) {
            setupLineChart(product.getPrices());
            productChangeRateChart.setVisibility(View.VISIBLE);
        }
        else productChangeRateChart.setVisibility(View.GONE);

        /* event setting */
        this.productImage.setOnClickListener(v -> onClickListener.onProductClick(product));
        this.productTitle.setOnClickListener(v -> onClickListener.onProductClick(product));
        this.productCart.setOnClickListener(v -> {
            //TODO 여기서 cart 이미지를 바꿔주는데, 이건 좀 별로라 생각 함.
            if (product.isWish()) {
                onClickListener.onDelete(product.getId());
                this.productCart.setImageResource(R.drawable.baseline_add_shopping_cart_24);
            }
            else {
                onClickListener.onInsert(product, product::setId);
                this.productCart.setImageResource(R.drawable.baseline_shopping_cart_24);
            }
        });
    }

    private void setupLineChart(List<Product.Price> prices) {
        // 1. 데이터 준비
        List<Entry> lowPriceEntries = new ArrayList<>();
        List<Entry> highPriceEntries = new ArrayList<>();
        List<String> xDate = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd", Locale.getDefault());

        Calendar iterCalendar = Calendar.getInstance();
        Calendar sixMonthsAgo = Calendar.getInstance();
        sixMonthsAgo.add(Calendar.MONTH, -6);
        int xIndex = 0;

        // 현재 날짜 추가
        final Product.Price cntPrice = prices.get(0);
        lowPriceEntries.add(new Entry(xIndex, cntPrice.getLPrice()));
        highPriceEntries.add(new Entry(xIndex, cntPrice.getHPrice()));
        xDate.add(dateFormat.format(iterCalendar.getTime()));

        // X축 값 매핑
        int prevLPrice = cntPrice.getLPrice();
        int prevHPrice = cntPrice.getHPrice();
        iterCalendar.add(Calendar.WEEK_OF_MONTH, -1);
        xIndex++;
        for (int i = 0; i < prices.size(); i++) {
            if (iterCalendar.before(sixMonthsAgo)) break;
            final Product.Price price = prices.get(i);
            Calendar iDate = price.getDate();
            final int cntLPrice = price.getLPrice();
            final int cntHPrice = price.getHPrice();
            final boolean lastIter = i == prices.size() - 1;

            if (iDate.before(iterCalendar) || lastIter){
                lowPriceEntries.add(new Entry(xIndex, prevLPrice));
                highPriceEntries.add(new Entry(xIndex, prevHPrice));
                xDate.add(dateFormat.format(iterCalendar.getTime()));
                iterCalendar.add(Calendar.WEEK_OF_MONTH, -1);
                if (lastIter) break;
                i--; xIndex++;
            }

            prevLPrice = cntLPrice;
            prevHPrice = cntHPrice;
        }

        Collections.reverse(xDate);
        for (int i = 0, j = xDate.size()-1; i <= j; i++, j--){
           Entry first = lowPriceEntries.get(i);
           Entry second = lowPriceEntries.get(j);
           float temp = first.getY();
           first.setY(second.getY());
           second.setY(temp);
        }

        // 2. 데이터셋 생성
        LineDataSet lowPriceDataSet = new LineDataSet(lowPriceEntries, "Low Price");
        LineDataSet highPriceDataSet = new LineDataSet(highPriceEntries, "High Price");

        // lPrice (파란색 선)
        lowPriceDataSet.setColor(Color.BLUE);
        lowPriceDataSet.setCircleColor(Color.BLUE);
        lowPriceDataSet.setCircleRadius(3f); // 포인트 크기
        lowPriceDataSet.setLineWidth(1f); // 선 두께
        lowPriceDataSet.setValueTextSize(6f); // 값 텍스트 크기
        lowPriceDataSet.setDrawFilled(true); // 영역 채우기 활성화
        lowPriceDataSet.setFillColor(Color.parseColor("#ADD8E6")); // 연한 파란색

        // hPrice (빨간색 선)
        highPriceDataSet.setColor(Color.RED);
        highPriceDataSet.setCircleColor(Color.RED);
        highPriceDataSet.setCircleRadius(3f);
        highPriceDataSet.setLineWidth(1f);
        highPriceDataSet.setValueTextSize(6f);

        // 3. 데이터 적용
        LineData lineData = new LineData(lowPriceDataSet, highPriceDataSet);
        productChangeRateChart.setData(lineData);

        // 4. X축 스타일 설정
        XAxis xAxis = productChangeRateChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // X축 위치 아래
        xAxis.setTextColor(Color.BLACK);
        xAxis.setTextSize(6f);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1); // 주 단위 간격
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int index = (int) value;
                if (index < 0 || index >= xDate.size()) {
                    return "";
                }
                return xDate.get(index);
            }
        });

        // X축 범위 제한 (6개월)
        xAxis.setAxisMinimum(0); // X축 최소값은 첫 번째 인덱스
        xAxis.setAxisMaximum(xDate.size()-1); // 마지막 인덱스까지 포함

        // 5. Y축 스타일 설정
        YAxis leftAxis = productChangeRateChart.getAxisLeft();
        leftAxis.setEnabled(false); // 왼쪽 Y축 비활성화

        YAxis rightAxis = productChangeRateChart.getAxisRight();
        rightAxis.setEnabled(false); // 오른쪽 Y축 비활성화

        productChangeRateChart.setTouchEnabled(false);
        productChangeRateChart.setDragEnabled(false);
        productChangeRateChart.setScaleEnabled(false);

        productChangeRateChart.getLegend().setEnabled(false);

        // 6. 기타 스타일 설정
        productChangeRateChart.setDrawGridBackground(false); // 배경 그리드 비활성화
        productChangeRateChart.getDescription().setEnabled(false); // 설명 제거
        productChangeRateChart.animateX(1000); // X축 애니메이션
        productChangeRateChart.invalidate(); // Chart 갱신
    }
}
