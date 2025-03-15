package com.quickBite.primary.repository;

import com.quickBite.primary.pojo.OneTimePassword;
import com.quickBite.primary.pojo.enums.VerificationTypeEnum;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface OneTimePasswordRepository extends MongoRepository<OneTimePassword, ObjectId> {

    @Query(value = "{" +
            "  'otpSentOn'  : ?0," +
            "  'otpCode' : ?1," +
            "  'verificationType' : ?2," +
            "  'expiredAt' : { '$gt' : ?3 }," +
            "  'expired' : false" +
            "}")
    OneTimePassword verifyOtp(String otpSentOn, String otpCode,
                              VerificationTypeEnum verificationType, LocalDateTime time);

    @Query(value = "{" +
            "  'otpSentOn'  : ?0," +
            "  'expired' : false" +
            "}")
    List<OneTimePassword> findAllNonExpiredOtp(String mobile);
}
