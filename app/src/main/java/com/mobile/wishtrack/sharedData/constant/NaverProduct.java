package com.mobile.wishtrack.sharedData.constant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NaverProduct {
    protected String title;
    protected String link;
    protected String image;
    protected int lPrice;
    protected int hPrice;
    protected String mallName;
    protected int productId;
    protected NaverProductType naverProductType;
    protected String maker;
    protected String brand;
    protected String category1;
    protected String category2;
    protected String category3;
    protected String category4;
}
