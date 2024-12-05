package com.mobile.wishtrack.domain.model;

import com.mobile.wishtrack.sharedData.constant.NaverProduct;
import com.mobile.wishtrack.sharedData.constant.NaverProductType;

import java.util.Calendar;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = false)
public class Product extends NaverProduct {

    private final Integer id;
    private final List<Price> prices;
    private final boolean isWish;
    private final float changeRate;

    private Product(
            Integer id,
            List<Price> prices,
            boolean isWish,
            float changeRate,
            String title,
            String link,
            String image,
            int lPrice,
            int hPrice,
            String mallName,
            long productId,
            NaverProductType naverProductType,
            String maker,
            String brand,
            String category1,
            String category2,
            String category3,
            String category4
    ) {
        super(title, link, image, lPrice, hPrice, mallName, productId, naverProductType, maker, brand, category1, category2, category3, category4);
        this.id = id;
        this.prices = prices;
        this.isWish = isWish;
        this.changeRate = changeRate;
    }

    public static Product newInstance(
            Integer id,
            String title,
            String link,
            String image,
            int lPrice,
            int hPrice,
            String mallName,
            long productId,
            NaverProductType naverProductType,
            String maker,
            String brand,
            String category1,
            String category2,
            String category3,
            String category4,
            List<Price> prices,
            boolean isWish
    ) {
        float changeRate = prices.size() == 1 ? 0 : (float) (prices.get(0).lPrice - prices.get(1).lPrice) / prices.get(1).lPrice;
        return new Product(id, prices, isWish, changeRate, title, link, image, lPrice, hPrice, mallName, productId, naverProductType, maker, brand, category1, category2, category3, category4);
    }

    public static Product newInstance(
            Integer id,
            String title,
            String link,
            String image,
            String mallName,
            long productId,
            NaverProductType naverProductType,
            String maker,
            String brand,
            String category1,
            String category2,
            String category3,
            String category4,
            List<Price> prices,
            boolean isWish
    ) {
        final int lPrice = prices.isEmpty() ? 0 : prices.get(0).lPrice;
        final int hPrice = prices.isEmpty() ? 0 : prices.get(0).hPrice;
        return newInstance(id, title, link, image, lPrice, hPrice, mallName, productId, naverProductType, maker, brand, category1, category2, category3, category4, prices, isWish);
    }


    public static Product newInstance(
            String title,
            String link,
            String image,
            String mallName,
            long productId,
            NaverProductType naverProductType,
            String maker,
            String brand,
            String category1,
            String category2,
            String category3,
            String category4,
            List<Price> prices,
            boolean isWish
    ) {
        final int lPrice = prices.isEmpty() ? 0 : prices.get(0).lPrice;
        final int hPrice = prices.isEmpty() ? 0 : prices.get(0).hPrice;
        return newInstance(null, title, link, image, lPrice, hPrice, mallName, productId, naverProductType, maker, brand, category1, category2, category3, category4, prices, isWish);
    }

    @AllArgsConstructor
    @Getter
    public static class Price {
        private final Calendar date;
        private final int lPrice;
        private final int hPrice;
    }
}

