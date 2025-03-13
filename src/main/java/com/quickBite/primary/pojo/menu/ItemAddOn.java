package com.quickBite.primary.pojo.menu;

import com.quickBite.primary.pojo._BasicEntity;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;


@Setter
@Getter
@Document("i_itemAddOn")
public class ItemAddOn extends _BasicEntity {

    private ObjectId vendorId;
    private ObjectId restaurantId;
    private ObjectId categoryId;

    private String title;
    private double addOnPrice = 0.0;
    private Long sortOrder;
}