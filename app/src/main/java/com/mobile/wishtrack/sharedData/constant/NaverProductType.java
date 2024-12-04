package com.mobile.wishtrack.sharedData.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum NaverProductType {
    GENERAL_COMPARE(1, "일반상품", "가격비교 상품"),
    GENERAL_UNMATCHED(2, "일반상품", "가격비교 비매칭 일반상품"),
    GENERAL_MATCHED(3, "일반상품", "가격비교 매칭 일반상품"),
    USED_COMPARE(4, "중고상품", "가격비교 상품"),
    USED_UNMATCHED(5, "중고상품", "가격비교 비매칭 일반상품"),
    USED_MATCHED(6, "중고상품", "가격비교 매칭 일반상품"),
    DISCONTINUED_COMPARE(7, "단종상품", "가격비교 상품"),
    DISCONTINUED_UNMATCHED(8, "단종상품", "가격비교 비매칭 일반상품"),
    DISCONTINUED_MATCHED(9, "단종상품", "가격비교 매칭 일반상품"),
    UPCOMING_COMPARE(10, "판매예정상품", "가격비교 상품"),
    UPCOMING_UNMATCHED(11, "판매예정상품", "가격비교 비매칭 일반상품"),
    UPCOMING_MATCHED(12, "판매예정상품", "가격비교 매칭 일반상품");

    private final int id;
    private final String category;
    private final String description;

    public static NaverProductType fromId(int id) {
        for (NaverProductType type : values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid id: " + id);
    }
}
