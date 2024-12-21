package com.mobile.wishtrack.data.model.price;

import androidx.room.Embedded;
import androidx.room.TypeConverters;

import com.mobile.wishtrack.data.model.product.ProductEntity;

import java.util.List;

public class ProductWithPrices {

    public ProductWithPrices(ProductEntity product, List<PriceEntity> prices){
        this.product = product;
        this.prices = prices;
    }

    @Embedded
    private ProductEntity product;

    @TypeConverters(ListDateConverter.class)
    private List<PriceEntity> prices;

    public void setPrices(List<PriceEntity> prices) {
        this.prices = prices;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public List<PriceEntity> getPrices() {
        return prices;
    }

    public ProductEntity getProduct() {
        return product;
    }
}
