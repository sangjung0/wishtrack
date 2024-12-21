package com.mobile.wishtrack.domain.model;

import com.mobile.wishtrack.sharedData.constant.NaverParameterKind;
import com.mobile.wishtrack.sharedData.constant.NaverParameterSort;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

//TODO 이거 구조가 이상해짐. 추후 싱글톤으로 변경하는게 좋을 듯
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class QueryOptions {
    private int display;
    private int start;
    private NaverParameterSort sort;
    private String filter;
    private List<NaverParameterKind> exclude;
    private int minPrice;
    private int maxPrice;
    private int minRate;
    private int maxRate;

    public static QueryOptions newInstance() {
        return newInstance(NaverParameterSort.SIM, 0, 0, 0, 0);
    }

    public static QueryOptions newInstance(NaverParameterSort sort, int minPrice, int maxPrice, int minRate, int maxRate) {
        final int display = 40;
        final int start = 1;
        final String filter = "";
        final ArrayList<NaverParameterKind> exclude = new ArrayList<>();
        return new QueryOptions(display, start, sort, filter, exclude, minPrice, maxPrice, minRate, maxRate);
    }
}
