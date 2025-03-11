package com.quickBite.primary.pojo.enums;


import java.util.ArrayList;
import java.util.List;

public enum SettingEnum {

    Maintenance("Maintenance"),

    VendorLogin("VendorLogin"),
    CustomerLogin("CustomerLogin"),

    // Double Setting
    AppVersion("AppVersion"),

    // String Setting
    BaseUrl("BaseUrl"),
    NotificationEmailTo("NotificationEmailTo"),
    ;

    String type;

    SettingEnum(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }

    public static List<SettingEnum> getToggleSettingsKey() {
        List<SettingEnum> settingList = new ArrayList<>();
        settingList.add(Maintenance);
        settingList.add(VendorLogin);
        settingList.add(CustomerLogin);
        return settingList;
    }

    public static List<SettingEnum> getFloatSettingsKey() {
        List<SettingEnum> settingList = new ArrayList<>();
        settingList.add(AppVersion);
        return settingList;
    }

    public static List<SettingEnum> getStringSettingsKey() {
        List<SettingEnum> settingList = new ArrayList<>();
        settingList.add(BaseUrl);
        settingList.add(NotificationEmailTo);
        return settingList;
    }

}