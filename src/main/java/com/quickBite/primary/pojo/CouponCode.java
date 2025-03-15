package com.quickBite.primary.pojo;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Getter
@Setter
@Document(value = "couponCode")
public class CouponCode extends _BasicEntity {

    private ObjectId vendorId;
    private String title;
    private String couponCode; // Must Be Unique

    private LocalDate startDate;
    private LocalDate endDate;

    // discountValue = Discount Percentage
    private Double discountValue;

}
