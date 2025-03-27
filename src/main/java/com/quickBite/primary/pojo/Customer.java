package com.quickBite.primary.pojo;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Document(collection = "customer")
public class Customer extends _BaseUser {

    private ObjectId adminId;
    private ObjectId vendorId;
}

