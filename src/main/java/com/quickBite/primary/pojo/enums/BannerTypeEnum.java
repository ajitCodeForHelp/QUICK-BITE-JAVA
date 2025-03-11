package com.quickBite.primary.pojo.enums;

public enum BannerTypeEnum {
    DashboardTop("DashboardTop")
    ;

    String type;

    BannerTypeEnum(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
