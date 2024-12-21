package com.mobile.wishtrack.data.webAPI;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class DefaultIntAdapter extends TypeAdapter<Integer> {
    @Override
    public void write(JsonWriter out, Integer value) throws IOException {
        out.value(value);
    }

    @Override
    public Integer read(JsonReader in) throws IOException {
        try {
            String value = in.nextString();
            return value.isEmpty() ? 0 : Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
