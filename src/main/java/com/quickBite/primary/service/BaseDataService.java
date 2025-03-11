package com.quickBite.primary.service;

import com.quickBite.primary.pojo.Setting;
import com.quickBite.primary.pojo.UserAdmin;
import com.quickBite.primary.pojo.enums.GenderEnum;
import com.quickBite.primary.pojo.enums.RoleEnum;
import com.quickBite.primary.pojo.enums.SettingEnum;
import com.quickBite.primary.repository.SettingRepository;
import com.quickBite.primary.repository.UserAdminRepository;
import com.quickBite.utils.TextUtils;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class BaseDataService extends _BaseService {

    @PostConstruct
    private void postConstruct() {
        generateDefaultAdmins();
        createDefaultSettings();
    }

    @Autowired
    private UserAdminRepository userAdminRepository;

    @Autowired
    private SettingRepository settingRepository;

    public void generateDefaultAdmins() {
        {
            String superAdminEmail = "super@admin.com";
            UserAdmin userAdmin = userAdminRepository.findFirstByUsername(superAdminEmail);
            if (userAdmin == null) {
                userAdmin = new UserAdmin();
                userAdmin.setFirstName("SuperAdmin");
                userAdmin.setLastName("SuperAdmin");
                userAdmin.setGender(GenderEnum.Male);
                userAdmin.setIsdCode("+91");
                userAdmin.setMobile("9999999999");
                userAdmin.setEmail(superAdminEmail);
                userAdmin.setUsername(userAdmin.getEmail());
                userAdmin.setPwdSecure(TextUtils.getEncodedPassword("12"));
                userAdmin.setPwdText("12");
                userAdmin.setUserType(RoleEnum.ROLE_SUPER_ADMIN);
                userAdmin.setUniqueKey(TextUtils.getUniqueKey());
                userAdminRepository.save(userAdmin);
            }
        }

        {
            String adminEmail = "admin@admin.com";
            UserAdmin userAdmin = userAdminRepository.findFirstByUsername(adminEmail);
            if (userAdmin == null) {
                userAdmin = new UserAdmin();
                userAdmin.setFirstName("Admin");
                userAdmin.setLastName("Admin");
                userAdmin.setGender(GenderEnum.Male);
                userAdmin.setIsdCode("+91");
                userAdmin.setMobile("8888888888");
                userAdmin.setEmail(adminEmail);
                userAdmin.setUsername(userAdmin.getEmail());
                userAdmin.setPwdSecure(TextUtils.getEncodedPassword("12"));
                userAdmin.setPwdText("12");
                userAdmin.setUserType(RoleEnum.ROLE_ADMIN);
                userAdmin.setUniqueKey(TextUtils.getUniqueKey());
                userAdminRepository.save(userAdmin);
            }
        }
    }
    private void createDefaultSettings() {
        // Fetch Setting Data > Prepare Setting Map
        Map<SettingEnum, Setting> settingMap = new LinkedHashMap<>();
        List<Setting> settingList = settingRepository.findAll();
        for (Setting setting : settingList) {
            settingMap.put(setting.getSettingKey(), setting);
        }
        List<Setting> settingToBeUpdating = new ArrayList<>();
        for (SettingEnum settingEnum : SettingEnum.getToggleSettingsKey()) {
            if (!settingMap.containsKey(settingEnum)) {
                Setting setting = new Setting();
                setting.setSettingKey(settingEnum);
                setting.setSettingValue("0");
                settingToBeUpdating.add(setting);
            }
        }
        for (SettingEnum settingEnum : SettingEnum.getFloatSettingsKey()) {
            if (!settingMap.containsKey(settingEnum)) {
                Setting setting = new Setting();
                setting.setSettingKey(settingEnum);
                setting.setSettingValue("0.0");
                settingToBeUpdating.add(setting);
            }
        }
        for (SettingEnum settingEnum : SettingEnum.getStringSettingsKey()) {
            if (!settingMap.containsKey(settingEnum)) {
                Setting setting = new Setting();
                setting.setSettingKey(settingEnum);
                setting.setSettingValue("");
                settingToBeUpdating.add(setting);
            }
        }
        if (!settingToBeUpdating.isEmpty()) {
            settingRepository.saveAll(settingToBeUpdating);
        }
    }
}