package com.quickBite.primary.repository;

import com.quickBite.primary.pojo.Setting;
import com.quickBite.primary.pojo.enums.SettingEnum;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SettingRepository extends MongoRepository<Setting, ObjectId> {

    Setting findBySettingKey(SettingEnum settingKey);
}