package com.quickBite.primary.repository;

import com.quickBite.primary.pojo.AppCode;
import com.quickBite.primary.pojo.Banner;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AppCodeRepository extends MongoRepository<AppCode, ObjectId> {
    AppCode findFirstByAppCodeId(String appCodeId);

    List<AppCode> findByActive(boolean active);
}
