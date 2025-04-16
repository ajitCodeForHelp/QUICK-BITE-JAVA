package com.quickBite.primary.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Setter
@Getter
@Document(collection = "vendor")
public class Vendor extends _BaseUser {

    private ObjectId adminId;
    private Long seqId;
    private Database databaseDetail;
    private ContactDetail contactDetail;

    // Restaurant Limit Vendor Can Make
    private Integer restaurantLimit = 1;
    private List<ObjectId> restaurantIdList;


    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Database {
        private String databaseUri;
        private String databaseName;
    }
    @Setter
    @Getter
    public static class ContactDetail {
        private String contactName;
        private String contactNumber;
        private String contactEmail;
    }

}

