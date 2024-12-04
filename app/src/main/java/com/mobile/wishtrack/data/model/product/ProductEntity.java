package com.mobile.wishtrack.data.model.product;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.mobile.wishtrack.sharedData.constant.NaverProduct;
import com.mobile.wishtrack.sharedData.constant.NaverProductType;

import java.util.Calendar;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity(
        tableName = "product",
        indices = @Index(value = "date", orders = Index.Order.DESC)
)
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductEntity extends NaverProduct {

    @PrimaryKey
    private int id;
    private Calendar wishDate;

    @Ignore
    @Override
    public int getHPrice() {
        return super.getHPrice();
    }

    @Ignore
    @Override
    public int getLPrice() {
        return super.getLPrice();
    }

    public ProductEntity(
            Calendar wishDate,
            String title,
            String link,
            String image,
            int lPrice,
            int hPrice,
            String mallName,
            int productId,
            NaverProductType naverProductType,
            String maker,
            String brand,
            String category1,
            String category2,
            String category3,
            String category4
    ) {
        super(title, link, image, lPrice, hPrice, mallName, productId, naverProductType, maker, brand, category1, category2, category3, category4);
        this.wishDate = wishDate;
    }

    public ProductEntity(
            String title,
            String link,
            String image,
            int lPrice,
            int hPrice,
            String mallName,
            int productId,
            NaverProductType naverProductType,
            String maker,
            String brand,
            String category1,
            String category2,
            String category3,
            String category4
    ) {
        this(Calendar.getInstance(), title, link, image, lPrice, hPrice, mallName, productId, naverProductType, maker, brand, category1, category2, category3, category4);
    }
}
