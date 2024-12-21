package com.mobile.wishtrack.data.model.product;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.mobile.wishtrack.sharedData.constant.NaverProduct;
import com.mobile.wishtrack.sharedData.constant.NaverProductType;

import java.util.Calendar;

import lombok.EqualsAndHashCode;

@Entity(
        tableName = "product",
        indices = {
                @Index(value = "wishDate", orders = Index.Order.DESC),
                @Index(value = "productId")
        }
)
@EqualsAndHashCode(callSuper = false)
public class ProductEntity extends NaverProduct {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private Calendar wishDate;

    @Ignore
    private int lPrice;

    @Ignore
    private int hPrice;

    public int getId() {
        return id;
    }

    public Calendar getWishDate(){
        return wishDate;
    }

    public ProductEntity(
            int id,
            Calendar wishDate,
            String title,
            String link,
            String image,
            String mallName,
            long productId,
            NaverProductType productType,
            String maker,
            String brand,
            String category1,
            String category2,
            String category3,
            String category4
    ) {
        super(title, link, image, 0, 0, mallName, productId, productType, maker, brand, category1, category2, category3, category4);
        this.id = id;
        this.wishDate = wishDate;
    }

    @Ignore
    public ProductEntity(
            Calendar wishDate,
            String title,
            String link,
            String image,
            int lPrice,
            int hPrice,
            String mallName,
            long productId,
            NaverProductType productType,
            String maker,
            String brand,
            String category1,
            String category2,
            String category3,
            String category4
    ) {
        super(title, link, image, lPrice, hPrice, mallName, productId, productType, maker, brand, category1, category2, category3, category4);
        this.wishDate = wishDate;
    }

    @Ignore
    public ProductEntity(
            String title,
            String link,
            String image,
            int lPrice,
            int hPrice,
            String mallName,
            long productId,
            NaverProductType productType,
            String maker,
            String brand,
            String category1,
            String category2,
            String category3,
            String category4
    ) {
        this(Calendar.getInstance(), title, link, image, lPrice, hPrice, mallName, productId, productType, maker, brand, category1, category2, category3, category4);
    }
}
