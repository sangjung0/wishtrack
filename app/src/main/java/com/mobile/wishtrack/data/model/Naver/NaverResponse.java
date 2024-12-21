package com.mobile.wishtrack.data.model.Naver;

import com.mobile.wishtrack.sharedData.constant.NaverProduct;

import java.util.List;

import lombok.Getter;

@Getter
public class NaverResponse {

    private String lastBuildDate;
    private int total;
    private int start;
    private int display;
    private List<NaverProduct> items;
}
