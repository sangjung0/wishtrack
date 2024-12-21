package com.mobile.wishtrack.data.model.price;

import androidx.room.TypeConverter;

import java.util.Calendar;

public class DateConverter {
    @TypeConverter
    public static long fromCalendar(Calendar calendar) {
        return calendar == null ? 0 : calendar.getTimeInMillis();
    }

    @TypeConverter
    public static Calendar toCalendar(long timestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        return calendar;
    }
}
