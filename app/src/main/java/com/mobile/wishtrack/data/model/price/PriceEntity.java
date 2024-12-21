package com.mobile.wishtrack.data.model.price;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;

import com.mobile.wishtrack.data.model.product.ProductEntity;

import java.util.Calendar;

@Entity(
        tableName = "price",
        indices = @Index(value = "date", orders = Index.Order.DESC),
        foreignKeys = @ForeignKey(
                entity = ProductEntity.class,
                parentColumns = "id",
                childColumns = "pid",
                onDelete = ForeignKey.CASCADE
        ),
        primaryKeys = {"pid", "date"}
)
public class PriceEntity {

    private int pid;

    @NonNull
    private Calendar date;
    private int lprice;
    private int hprice;

    public PriceEntity(int pid, @NonNull Calendar date, int lprice, int hprice) {
        this.pid = pid;
        this.date = date;
        this.lprice = lprice;
        this.hprice = hprice;
    }

    @Ignore
    public PriceEntity(@NonNull Calendar date, int lprice, int hprice) {
        this.date = date;
        this.lprice = lprice;
        this.hprice = hprice;
    }

    public int getPid() {
        return pid;
    }

    public Calendar getDate() {
        return date;
    }

    public int getLprice() {
        return lprice;
    }

    public int getHprice() {
        return hprice;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public void setLPrice(int lPrice) {
        this.lprice = lPrice;
    }

    public void setHPrice(int hPrice) {
        this.hprice = hPrice;
    }
}
