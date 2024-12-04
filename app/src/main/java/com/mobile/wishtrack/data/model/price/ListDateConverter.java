package com.mobile.wishtrack.data.model.price;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ListDateConverter {
    @TypeConverter
    public static List<PriceEntity> fromJson(String json) {
        Type listType = new TypeToken<List<PriceEntity>>() {}.getType();
        return new Gson().fromJson(json, listType);
    }

    @TypeConverter
    public static String toJson(List<PriceEntity> prices) {
        return new Gson().toJson(prices);
    }
}
