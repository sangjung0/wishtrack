package com.mobile.wishtrack.data.webAPI;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonProvider {
    public static Gson getGson() {
        return new GsonBuilder()
                .registerTypeAdapter(Integer.class, new DefaultIntAdapter())
                .registerTypeAdapter(int.class, new DefaultIntAdapter())
                .create();
    }
}
