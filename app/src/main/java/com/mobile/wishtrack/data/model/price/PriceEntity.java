package com.mobile.wishtrack.data.model.price;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

import com.mobile.wishtrack.data.model.product.ProductEntity;

import java.util.Calendar;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(
        tableName = "price",
        indices = @Index(value = "date", orders = Index.Order.DESC),
        foreignKeys = @ForeignKey(
                entity = ProductEntity.class,
                parentColumns = "id",
                childColumns = "pid",
                onDelete = ForeignKey.CASCADE
        ),
        primaryKeys = {"productId", "date"}
)
@NoArgsConstructor
@Getter
@Setter
public class PriceEntity {
    private int pid;
    private Calendar date;
    private int lPrice;
    private int hPrice;

    public PriceEntity(Calendar date, int lPrice, int hPrice) {
        this.date = date;
        this.lPrice = lPrice;
        this.hPrice = hPrice;
    }
}
