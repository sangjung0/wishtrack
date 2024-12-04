package com.mobile.wishtrack.data.model.product;

import androidx.room.TypeConverter;

import com.mobile.wishtrack.sharedData.constant.NaverProductType;

public class ProductTypeConverter {
    @TypeConverter
    public static NaverProductType fromId(int id) {
        return NaverProductType.fromId(id);
    }

    @TypeConverter
    public static int toId(NaverProductType naverProductType) {
        return naverProductType.getId();
    }
}
