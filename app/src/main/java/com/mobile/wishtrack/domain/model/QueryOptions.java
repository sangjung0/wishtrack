package com.mobile.wishtrack.domain.model;

import com.mobile.wishtrack.sharedData.constant.NaverParameterKind;
import com.mobile.wishtrack.sharedData.constant.NaverParameterSort;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class QueryOptions {
    private final int display;
    private final int start;
    private final NaverParameterSort sort;
    private final String filter;
    private final List<NaverParameterKind> exclude;
    private final int minPrice;
    private final int maxPrice;
    private final int minRate;
    private final int maxRate;

    public static QueryOptions newInstance() {
        return newInstance(NaverParameterSort.SIM, 0, 0, 0, 0);
    }

    public static QueryOptions newInstance(NaverParameterSort sort, int minPrice, int maxPrice, int minRate, int maxRate) {
        final int display = 10;
        final int start = 1;
        final String filter = "";
        final ArrayList<NaverParameterKind> exclude = new ArrayList<>();
        return new QueryOptions(display, start, sort, filter, exclude, minPrice, maxPrice, minRate, maxRate);
    }
}
