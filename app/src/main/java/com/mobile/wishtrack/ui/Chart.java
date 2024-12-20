package com.mobile.wishtrack.ui;

import android.graphics.Color;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.mobile.wishtrack.domain.model.Product;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class Chart {
    public static void setupLineChart(LineChart chart, List<Product.Price> prices) {
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
        chart.setData(lineData);

        // 4. X축 스타일 설정
        XAxis xAxis = chart.getXAxis();
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
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setEnabled(false); // 왼쪽 Y축 비활성화

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false); // 오른쪽 Y축 비활성화

        chart.setTouchEnabled(false);
        chart.setDragEnabled(false);
        chart.setScaleEnabled(false);

        chart.getLegend().setEnabled(false);

        // 6. 기타 스타일 설정
        chart.setDrawGridBackground(false); // 배경 그리드 비활성화
        chart.getDescription().setEnabled(false); // 설명 제거
        chart.animateX(1000); // X축 애니메이션
        chart.invalidate(); // Chart 갱신
    }
}
