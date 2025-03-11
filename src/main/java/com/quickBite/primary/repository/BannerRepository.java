package com.quickBite.primary.repository;

import com.quickBite.primary.pojo.Banner;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BannerRepository extends MongoRepository<Banner, ObjectId> {


    List<Banner> findByActive(boolean active);
}
