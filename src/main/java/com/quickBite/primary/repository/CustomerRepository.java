//package com.quickBite.primary.repository;
//
//import com.quickBite.primary.pojo.Customer;
//import org.bson.types.ObjectId;
//import org.springframework.data.mongodb.repository.MongoRepository;
//
//import java.util.List;
//
//public interface CustomerRepository extends MongoRepository<Customer, ObjectId> {
//    Customer findFirstByUsername(String username);
//
//    boolean existsByIsdCodeAndMobile(String isdCode, String mobile);
//
//    boolean existsByEmail(String email);
//
//    Customer findFirstByIsdCodeAndMobileAndId(String isdCode, String mobile, ObjectId id);
//
//    Customer findFirstByEmailAndId(String email, ObjectId id);
//
//    List<Customer> findByActive(boolean active);
//    Customer findFirstByIsdCodeAndMobile(String isdCode, String mobile);
//
//}
