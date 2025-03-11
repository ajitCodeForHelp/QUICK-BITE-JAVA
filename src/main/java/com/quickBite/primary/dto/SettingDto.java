package com.quickBite.primary.dto;

import com.quickBite.primary.pojo.enums.SettingEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class SettingDto {

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateSetting {
        private SettingEnum settingKey;
        private String settingValue;
    }

    @Setter
    @Getter
    public static class SettingList {
        private Map<SettingEnum, String> toggleSettings = new LinkedHashMap<>();
        private Map<SettingEnum, String> floatSettings = new LinkedHashMap<>();
        private Map<SettingEnum, String> stringSettings = new LinkedHashMap<>();
    }

    @Setter
    @Getter
    public static class UpdateSettings {
        private List<UpdateSetting> settings;
    }

}
