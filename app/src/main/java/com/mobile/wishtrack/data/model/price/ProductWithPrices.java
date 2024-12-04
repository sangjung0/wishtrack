package com.mobile.wishtrack.data.model.price;

import androidx.room.Embedded;
import androidx.room.TypeConverters;

import com.mobile.wishtrack.data.model.product.ProductEntity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductWithPrices {
    @Embedded
    private ProductEntity product;

    @TypeConverters(ListDateConverter.class)
    private List<PriceEntity> prices;
}
