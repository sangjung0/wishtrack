package com.mobile.wishtrack.sharedData.constant;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NaverProduct {
    private String title;
    private String link;
    private String image;
    private int lprice;
    private int hprice;
    private String mallName;
    private long productId;
    private NaverProductType productType;
    private String maker;
    private String brand;
    private String category1;
    private String category2;
    private String category3;
    private String category4;

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getImage() {
        return image;
    }

    public int getLprice() {
        return lprice;
    }

    public int getHprice() {
        return hprice;
    }

    public String getMallName() {
        return mallName;
    }

    public long getProductId() {
        return productId;
    }

    public NaverProductType getProductType() {
        return productType;
    }

    public String getMaker() {
        return maker;
    }

    public String getBrand() {
        return brand;
    }

    public String getCategory1() {
        return category1;
    }

    public String getCategory2() {
        return category2;
    }

    public String getCategory3() {
        return category3;
    }

    public String getCategory4() {
        return category4;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setLprice(int lPrice) {
        this.lprice = lPrice;
    }

    public void setHprice(int hPrice) {
        this.hprice = hPrice;
    }

    public void setMallName(String mallName) {
        this.mallName = mallName;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public void setProductType(NaverProductType productType) {
        this.productType = productType;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setCategory1(String category1) {
        this.category1 = category1;
    }

    public void setCategory2(String category2) {
        this.category2 = category2;
    }

    public void setCategory3(String category3) {
        this.category3 = category3;
    }

    public void setCategory4(String category4) {
        this.category4 = category4;
    }
}

