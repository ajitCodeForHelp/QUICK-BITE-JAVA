package com.quickBite.primary.pojo;


import com.quickBite.primary.pojo.enums.SettingEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Document(value = "setting")
public class Setting extends _BasicEntity {

    private SettingEnum settingKey;

    private String settingValue;
}
