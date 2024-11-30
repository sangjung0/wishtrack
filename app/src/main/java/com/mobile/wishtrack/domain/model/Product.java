package com.mobile.wishtrack.domain.model;

import java.util.Calendar;
import java.util.List;

public class Product {
    private final int id;
    private final String title;
    private final String link;
    private final String imageUrl;
    private final List<Calendar> dates;
    private final List<Integer> lPrices;
    private final List<Integer> hPrices;
    private final String mallName;
    private final boolean isWish;
    private final float changeRate;

    public static Product newInstance(
            int id,
            String title,
            String link,
            String imageUrl,
            List<Calendar> dates,
            List<Integer> lPrices,
            List<Integer> hPrices,
            String mallName,
            boolean isWish
    ) {
        float changeRate = lPrices.size() == 1 ? 0 : (float) (lPrices.get(0) - lPrices.get(1)) / lPrices.get(1);
        return new Product(id, title, link, imageUrl, dates, lPrices, hPrices, mallName, isWish, changeRate);
    }

    private Product(
            int id,
            String title,
            String link,
            String imageUrl,
            List<Calendar> dates,
            List<Integer> lPrices,
            List<Integer> hPrices,
            String mallName,
            boolean isWish,
            float changeRate
    ) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.imageUrl = imageUrl;
        this.dates = dates;
        this.lPrices = lPrices;
        this.hPrices = hPrices;
        this.mallName = mallName;
        this.isWish = isWish;
        this.changeRate = changeRate;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public List<Calendar> getDates() {
        return dates;
    }

    public List<Integer> getLPrices() {
        return lPrices;
    }

    public List<Integer> getHPrices() {
        return hPrices;
    }

    public String getMallName() {
        return mallName;
    }

    public boolean isWish() {
        return isWish;
    }

    public float getChangeRate() {
        return changeRate;
    }
}

