package com.quickBite.primary.pojo.menu;

import com.quickBite.primary.pojo._BasicEntity;
import com.quickBite.primary.pojo.enums.VegNonVegEnum;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Setter
@Getter
@Document("i_item")
public class Item extends _BasicEntity {

    private ObjectId vendorId;
    private ObjectId restaurantId;
    private ObjectId parentCategoryId;
    private ObjectId subCategoryId;

    private String title;
    private String description;
    private VegNonVegEnum vegNonVeg;
    private String itemImageUrl;

    private String sticker;

    // salePrice always less than or equal to maximumRetailPrice
    private double maximumRetailPrice;
    private double salePrice; // this will be final price

    // requiredAddOnList > free complementary addon which are served with item.
    private List<ObjectId> requiredAddOnList;
    // OptionalAddOnList > Optional addon which are served with item > But with extra cost included.
    private List<ObjectId> optionalAddOnList;

}