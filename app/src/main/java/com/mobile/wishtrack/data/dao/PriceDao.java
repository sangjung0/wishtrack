package com.mobile.wishtrack.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.mobile.wishtrack.data.model.price.PriceEntity;

import java.util.Calendar;
import java.util.List;

@Dao
public interface PriceDao {

    @Insert
    void insert(PriceEntity price);

    @Update
    void update(PriceEntity price);

    @Delete
    void delete(PriceEntity price);

    @Query("SELECT * FROM Price ORDER BY date DESC")
    List<PriceEntity> getPricesDESC();

    @Query("SELECT * FROM Price WHERE pId = :productId ORDER BY date DESC")
    List<PriceEntity> getPricesForProductId(int productId);

    @Query("SELECT * FROM Price WHERE pId = :productId AND date = :date")
    PriceEntity getPriceForProductByDate(int productId, Calendar date);
}
