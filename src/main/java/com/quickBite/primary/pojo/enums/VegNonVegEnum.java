package com.quickBite.primary.pojo.enums;

public enum VegNonVegEnum {
    Veg("Veg"),
    NonVeg("NonVeg"),
    None("None"),
    ;

    String type;

    VegNonVegEnum(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
