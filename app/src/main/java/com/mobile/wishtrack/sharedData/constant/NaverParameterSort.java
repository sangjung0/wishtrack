package com.mobile.wishtrack.sharedData.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NaverParameterSort {
    SIM("sim"),
    DATE("date"),
    DSC("dsc"),
    ASC("asc"),

    RASC("rasc"),
    RDSC("rdsc");

    private final String description;
}
