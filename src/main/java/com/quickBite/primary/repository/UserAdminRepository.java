package com.quickBite.primary.repository;

import com.quickBite.primary.pojo.UserAdmin;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UserAdminRepository extends MongoRepository<UserAdmin, ObjectId> {

    boolean existsByIsdCodeAndMobile(String isdCode, String mobile);

    boolean existsByEmail(String email);

    @Query(value = "{ $and  :[{'isd' : {'$eq' : ?0 }},{'mobile' : {'$eq' : ?1 }} ,{'id' : { '$ne' : ?2 }} ]}")
    UserAdmin findFirstByIsdAndMobileAndId(String isdCode, String mobile, ObjectId id);

    @Query(value = "{ $and  :[{'email' : {'$eq' : ?0 }},{'id' : {'$ne' : ?1 }} ]}")
    UserAdmin findFirstByEmailAndId(String email, ObjectId id);

    UserAdmin findFirstByUsername(String username);

}
