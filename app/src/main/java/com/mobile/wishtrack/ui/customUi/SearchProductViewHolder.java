package com.mobile.wishtrack.ui.customUi;

import android.graphics.Color;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class SearchProductViewHolder extends RecyclerView.ViewHolder {
    private ImageView productImage;
    private TextView productTitle;
    private ImageView star1, star2, star3, star4, star5;
    private TextView starValue;
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
        this.star1 = this.view.findViewById(R.id.star1);
        this.star2 = this.view.findViewById(R.id.star2);
        this.star3 = this.view.findViewById(R.id.star3);
        this.star4 = this.view.findViewById(R.id.star4);
        this.star5 = this.view.findViewById(R.id.star5);
        this.starValue = this.view.findViewById(R.id.startValue);
        this.productPrice = this.view.findViewById(R.id.productPrice);
        this.productChangeRate = this.view.findViewById(R.id.productChangeRate);
        this.productChangeRateArrow = this.view.findViewById(R.id.productChangeRateArrow);
        this.productCart = this.view.findViewById(R.id.productCart);
        this.productChangeRateChart = this.view.findViewById(R.id.productChangeRateChart);
    }

    public void bind(Product product) {
        /* setData */
        Glide.with(view.getContext()).load(product.getImageUrl()).into(this.productImage);
        this.productTitle.setText(product.getTitle());
        this.productPrice.setText(String.valueOf(product.getLPrices().get(0)));
        this.productChangeRate.setText(String.valueOf(product.getChangeRate()));
        this.productChangeRate.setTextColor(product.getChangeRate() > 0 ? view.getResources().getColor(R.color.increase_price) : view.getResources().getColor(R.color.decrease_price));
        this.productChangeRateArrow.setImageResource(product.getChangeRate() > 0 ? R.drawable.baseline_keyboard_arrow_up_24 : R.drawable.baseline_keyboard_arrow_down_24);
        this.productChangeRateArrow.setColorFilter(product.getChangeRate() > 0 ? view.getResources().getColor(R.color.increase_price) : view.getResources().getColor(R.color.decrease_price));
        this.productCart.setImageResource(product.isWish() ? R.drawable.baseline_shopping_cart_24 : R.drawable.baseline_add_shopping_cart_24);
        setupLineChart(product.getDates(), product.getLPrices(), product.getHPrices());
    }

    private void setupLineChart(List<Calendar> date, List<Integer> lPrice, List<Integer> hPrice) {
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
        lowPriceEntries.add(new Entry(xIndex, lPrice.get(0)));
        highPriceEntries.add(new Entry(xIndex, hPrice.get(0)));
        xDate.add(dateFormat.format(iterCalendar.getTime()));

        // X축 값 매핑
        int prevLPrice = lPrice.get(0);
        int prevHPrice = hPrice.get(0);
        iterCalendar.add(Calendar.WEEK_OF_MONTH, -1);
        xIndex++;
        for (int i = 0; i < date.size(); i++) {
            if (iterCalendar.before(sixMonthsAgo)) break;
            Calendar iDate = date.get(i);
            final int cntLPrice = lPrice.get(i);
            final int cntHPrice = hPrice.get(i);

            if (iDate.before(iterCalendar)){
                lowPriceEntries.add(new Entry(xIndex, prevLPrice));
                highPriceEntries.add(new Entry(xIndex, prevHPrice));
                xDate.add(dateFormat.format(iterCalendar.getTime()));
                iterCalendar.add(Calendar.WEEK_OF_MONTH, -1);
                i--; xIndex++;
            }

            prevLPrice = cntLPrice;
            prevHPrice = cntHPrice;
        }

        // 2. 데이터셋 생성
        LineDataSet lowPriceDataSet = new LineDataSet(lowPriceEntries, "Low Price");
        LineDataSet highPriceDataSet = new LineDataSet(highPriceEntries, "High Price");

        // lPrice (파란색 선)
        lowPriceDataSet.setColor(Color.BLUE);
        lowPriceDataSet.setCircleColor(Color.BLUE);
        lowPriceDataSet.setCircleRadius(6f); // 포인트 크기
        lowPriceDataSet.setLineWidth(2f); // 선 두께
        lowPriceDataSet.setValueTextSize(10f); // 값 텍스트 크기
        lowPriceDataSet.setDrawFilled(true); // 영역 채우기 활성화
        lowPriceDataSet.setFillColor(Color.parseColor("#ADD8E6")); // 연한 파란색

        // hPrice (빨간색 선)
        highPriceDataSet.setColor(Color.RED);
        highPriceDataSet.setCircleColor(Color.RED);
        highPriceDataSet.setCircleRadius(6f);
        highPriceDataSet.setLineWidth(2f);
        highPriceDataSet.setValueTextSize(10f);

        // 3. 데이터 적용
        LineData lineData = new LineData(lowPriceDataSet, highPriceDataSet);
        productChangeRateChart.setData(lineData);

        // 4. X축 스타일 설정
        XAxis xAxis = productChangeRateChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // X축 위치 아래
        xAxis.setTextColor(Color.BLACK);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1); // 주 단위 간격
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return xDate.get((int) value);
            }
        });

        // X축 범위 제한 (6개월)
        xAxis.setAxisMinimum(0); // X축 최소값은 첫 번째 인덱스
        xAxis.setAxisMaximum(xDate.size()-1); // 마지막 인덱스까지 포함

        // 5. Y축 스타일 설정
        YAxis leftAxis = productChangeRateChart.getAxisLeft();
        leftAxis.setTextColor(Color.BLACK);
        leftAxis.setDrawGridLines(true);

        YAxis rightAxis = productChangeRateChart.getAxisRight();
        rightAxis.setEnabled(false); // 오른쪽 Y축 비활성화

        // 6. 기타 스타일 설정
        productChangeRateChart.setDrawGridBackground(false); // 배경 그리드 비활성화
        productChangeRateChart.setNoDataText("No data available"); // 데이터가 없을 때 표시할 텍스트
        productChangeRateChart.getDescription().setEnabled(false); // 설명 제거
        productChangeRateChart.animateX(1000); // X축 애니메이션
        productChangeRateChart.invalidate(); // Chart 갱신
    }
}
