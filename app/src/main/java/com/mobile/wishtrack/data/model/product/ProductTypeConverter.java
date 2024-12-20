package com.mobile.wishtrack.data.model.product;

import androidx.room.TypeConverter;

import com.mobile.wishtrack.sharedData.constant.NaverProductType;

public class ProductTypeConverter {
    @TypeConverter
    public static NaverProductType fromId(int id) {
        if (id == 0) return null;
        return NaverProductType.fromId(id);
    }

    @TypeConverter
    public static int toId(NaverProductType naverProductType) {
        if (naverProductType == null) return 0;
        return naverProductType.getId();
    }
}
