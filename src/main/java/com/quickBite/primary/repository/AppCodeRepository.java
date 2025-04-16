package com.quickBite.primary.repository;

import com.quickBite.primary.pojo.AppCode;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AppCodeRepository extends MongoRepository<AppCode, ObjectId> {
    AppCode findFirstByAppCodeId(String appCodeId);

    AppCode findFirstByRestaurantId(ObjectId restaurantId);

    List<AppCode> findByActive(boolean active);
}
