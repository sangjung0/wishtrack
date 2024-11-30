package com.mobile.wishtrack.domain.model;

import java.util.ArrayList;
import java.util.List;

public class QueryOptions {
    private final int display;
    private final int start;
    private final Sort sort;
    private final String filter;
    private final List<Kind> exclude;
    private final int minPrice;
    private final int maxPrice;
    private final int minRate;
    private final int maxRate;

    public static QueryOptions newInstance() {
        return newInstance(Sort.SIM, 0, 0, 0, 0);
    }

    public static QueryOptions newInstance(Sort sort, int minPrice, int maxPrice, int minRate, int maxRate) {
        final int display = 100;
        final int start = 0;
        final String filter = "";
        final ArrayList<Kind> exclude = new ArrayList<>();
        return new QueryOptions(display, start, sort, filter, exclude, minPrice, maxPrice, minRate, maxRate);
    }

    private QueryOptions(int display, int start, Sort sort, String filter, List<Kind> exclude, int minPrice, int maxPrice, int minRate, int maxRate) {
        this.display = display;
        this.start = start;
        this.sort = sort;
        this.filter = filter;
        this.exclude = exclude;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.minRate = minRate;
        this.maxRate = maxRate;
    }



    public int getDisplay() {
        return display;
    }

    public int getStart() {
        return start;
    }

    public Sort getSort() {
        return sort;
    }

    public String getFilter() {
        return filter;
    }

    public List<Kind> getExclude() {
        return exclude;
    }

    public int getMinPrice() {
        return minPrice;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    public int getMinRate() {
        return minRate;
    }

    public int getMaxRate() {
        return maxRate;
    }

    public enum Sort {
        SIM("sim"),
        DATE("date"),
        DSC("dsc"),
        ASC("asc"),

        RASC("rasc"),
        RDSC("rdsc");

        private final String description;

        Sort(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    public enum Kind {
        USED("used"),
        RENTAL("rental"),
        CBSHOP("cbshop");

        private final String description;

        Kind(String description) {
            this.description = description;
        }

        public String getDescription(){
            return description;
        }
    }
}
