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
@Document("menu")
public class Menu extends _BasicEntity {

    private ObjectId vendorId;
    private ObjectId restaurantId;

    private List<MenuItems> menuItemsListList;

    @Setter
    @Getter
    public static class MenuItems {
        private MenuCategory parentCategory;
        private MenuCategory subCategory;
        private String id;
        private String title;
        private String description;
        private VegNonVegEnum vegNonVeg;
        private String itemImageUrl;

        private String sticker;

        private double maximumRetailPrice;
        private double salePrice;

        private List<MenuItemAddOn> requiredAddOnList;
        private List<MenuItemAddOn> optionalAddOnList;
    }

    @Setter
    @Getter
    public static class MenuCategory {
        private String id;
        private String title;
        private String description;
        private Long sortOrder;
        private String categoryIconUrl;
    }

    @Setter
    @Getter
    public static class MenuItemAddOn {
        private String id;
        private String title;
        private double addOnPrice;
        private Long sortOrder;
    }

}