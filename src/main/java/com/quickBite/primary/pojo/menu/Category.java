package com.quickBite.primary.pojo.menu;

import com.quickBite.primary.pojo._BasicEntity;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Setter
@Getter
@Document("i_category")
public class Category extends _BasicEntity {

    private ObjectId vendorId;
    private ObjectId restaurantId;
    private ObjectId parentCategoryId;

    private String title;
    private String description;
    private Long sortOrder;
    private String categoryIconUrl;
    private List<String> categoryImageUrls;

}