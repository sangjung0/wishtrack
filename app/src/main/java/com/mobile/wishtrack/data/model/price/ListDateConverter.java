package com.mobile.wishtrack.data.model.price;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.List;

public class ListDateConverter {

    private static final Gson gsonFromJson = new GsonBuilder()
            .registerTypeAdapter(PriceEntity.class, (JsonDeserializer<PriceEntity>) (jsonElement, type, context) -> {
                JsonObject jsonObject = jsonElement.getAsJsonObject();

                int pid = jsonObject.get("pid").getAsInt();
                long dateLong = jsonObject.get("date").getAsLong();
                int lPrice = jsonObject.get("lprice").getAsInt();
                int hPrice = jsonObject.get("hprice").getAsInt();

                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(dateLong);

                return new PriceEntity(pid, calendar, lPrice, hPrice);
            })
            .create();

    private static final Gson gsonToJson = new GsonBuilder()
            .registerTypeAdapter(PriceEntity.class, (JsonSerializer<PriceEntity>) (src, type, context) -> {
                JsonObject jsonObject = new JsonObject();

                jsonObject.addProperty("pid", src.getPid());
                jsonObject.addProperty("date", src.getDate().getTimeInMillis()); // Calendar -> long
                jsonObject.addProperty("lprice", src.getLprice());
                jsonObject.addProperty("hprice", src.getHprice());

                return jsonObject;
            })
            .create();


    @TypeConverter
    public static List<PriceEntity> fromJson(String json) {
        Type listType = new TypeToken<List<PriceEntity>>() {}.getType();
        return gsonFromJson.fromJson(json, listType);
    }

    @TypeConverter
    public static String toJson(List<PriceEntity> prices) {
        return gsonToJson.toJson(prices);
    }
}
