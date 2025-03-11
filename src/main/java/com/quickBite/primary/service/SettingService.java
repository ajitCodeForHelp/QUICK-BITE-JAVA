package com.quickBite.primary.service;

import com.quickBite.exception.BadRequestException;
import com.quickBite.primary.dto.SettingDto;
import com.quickBite.primary.pojo.Setting;
import com.quickBite.primary.pojo.enums.SettingEnum;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class SettingService extends _BaseService {

    public SettingDto.SettingList settingList() throws BadRequestException {
        // Fetch Setting Data > Prepare Setting Map
        Map<SettingEnum, Setting> settingMap = new LinkedHashMap<>();
        List<Setting> settingList = settingRepository.findAll();
        for (Setting setting : settingList) {
            settingMap.put(setting.getSettingKey(), setting);
        }

        SettingDto.SettingList systemSetting = new SettingDto.SettingList();
        for (SettingEnum settingEnum : SettingEnum.getToggleSettingsKey()) {
            if (settingMap.containsKey(settingEnum)) {
                systemSetting.getToggleSettings().put(
                        settingEnum, settingMap.get(settingEnum).getSettingValue().equals("1") ? "1" : "0"
                );
            } else {
                systemSetting.getToggleSettings().put(settingEnum, null);
            }
        }
        for (SettingEnum settingEnum : SettingEnum.getFloatSettingsKey()) {
            if (settingMap.containsKey(settingEnum)) {
                try {
                    systemSetting.getFloatSettings().put(
                            settingEnum, Double.parseDouble(settingMap.get(settingEnum).getSettingValue()) + ""
                    );
                } catch (Exception e) {
                    throw new BadRequestException("Error: The setting value '" + settingMap.get(settingEnum).getSettingValue() + "' is not a valid Double.");
                }
            } else {
                systemSetting.getFloatSettings().put(settingEnum, null);
            }
        }
        for (SettingEnum settingEnum : SettingEnum.getStringSettingsKey()) {
            if (settingMap.containsKey(settingEnum)) {
                systemSetting.getStringSettings().put(
                        settingEnum, settingMap.get(settingEnum).getSettingValue()
                );
            } else {
                systemSetting.getStringSettings().put(settingEnum, null);
            }
        }
        return systemSetting;
    }

    public void updateSettings(SettingDto.UpdateSettings updateSettings) throws BadRequestException {
        Map<SettingEnum, Setting> settingMap = new LinkedHashMap<>();
        List<Setting> settingList = settingRepository.findAll();
        for (Setting setting : settingList) {
            settingMap.put(setting.getSettingKey(), setting);
        }

        List<Setting> settingsToUpdate = new ArrayList<>();
        for (SettingDto.UpdateSetting setting : updateSettings.getSettings()) {
            Setting updateSetting = null;
            if (settingMap.containsKey(setting.getSettingKey())) {
                updateSetting = settingMap.get(setting.getSettingKey());
            }
            if (updateSetting == null) {
                updateSetting = new Setting();
                updateSetting.setSettingKey(setting.getSettingKey());
            }

            if (SettingEnum.getToggleSettingsKey().contains(setting.getSettingKey())) {
                updateSetting.setSettingValue(setting.getSettingValue().equals("1") ? "1" : "0");
            }
            if (SettingEnum.getFloatSettingsKey().contains(setting.getSettingKey())) {
                try {
                    updateSetting.setSettingValue(Double.parseDouble(setting.getSettingValue()) + "");
                } catch (Exception e) {
                    throw new BadRequestException("Error: The setting value '" + setting.getSettingValue() + "' is not a valid Long.");
                }
            }
            if (SettingEnum.getStringSettingsKey().contains(setting.getSettingKey())) {
                updateSetting.setSettingValue(setting.getSettingValue());
            }
            settingsToUpdate.add(updateSetting);
        }
        settingRepository.saveAll(settingsToUpdate);
    }
}
