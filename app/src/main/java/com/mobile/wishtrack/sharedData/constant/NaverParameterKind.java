package com.mobile.wishtrack.sharedData.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NaverParameterKind {
    USED("used"),
    RENTAL("rental"),
    CBSHOP("cbshop");

    private final String description;
}
