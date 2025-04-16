package com.quickBite.primary.repository;

import com.quickBite.primary.pojo.Vendor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface VendorRepository extends MongoRepository<Vendor, ObjectId> {

    Vendor findByUsername(String username);
    List<Vendor> findByActive(boolean active);
    List<Vendor> findByAdminId(ObjectId adminId);
}
