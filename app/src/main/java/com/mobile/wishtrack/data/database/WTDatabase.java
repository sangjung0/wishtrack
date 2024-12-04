package com.mobile.wishtrack.data.database;

import  android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.mobile.wishtrack.data.dao.PriceDao;
import com.mobile.wishtrack.data.dao.ProductDao;
import com.mobile.wishtrack.data.model.price.DateConverter;
import com.mobile.wishtrack.data.model.price.PriceEntity;
import com.mobile.wishtrack.data.model.product.ProductEntity;
import com.mobile.wishtrack.data.model.product.ProductTypeConverter;

@Database(entities = {ProductEntity.class, PriceEntity.class}, version = 1)
@TypeConverters({ProductTypeConverter.class, DateConverter.class})
public abstract class WTDatabase extends RoomDatabase {
    private static WTDatabase instance;

    public abstract ProductDao productDao();
    public abstract PriceDao priceDao();

    public static synchronized WTDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    WTDatabase.class, "wishtrack_database"
            ).build();
        }
        return instance;
    }
}
