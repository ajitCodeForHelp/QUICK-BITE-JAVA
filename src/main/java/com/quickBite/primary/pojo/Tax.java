package com.quickBite.primary.pojo;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Document(value = "tax")
public class Tax extends _BasicEntity {

    private ObjectId vendorId;
    private ObjectId restaurantId;

    private String title;
    private String taxPercentage;
}
