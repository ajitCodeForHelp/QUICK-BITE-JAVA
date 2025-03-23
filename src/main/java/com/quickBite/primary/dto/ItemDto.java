package com.quickBite.primary.dto;

import com.quickBite.primary.pojo.enums.VegNonVegEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ItemDto {

    @Getter
    @Setter
    public static class CreateItem {
        private String parentCategoryId;
        private String subCategoryId;

        private String title;
        private String description;
        private VegNonVegEnum vegNonVeg = VegNonVegEnum.None;
        private String itemImageUrl;

        private String sticker;

        private double maximumRetailPrice;
        private double salePrice;

        private List<String> requiredAddOnIdList;
        private List<String> optionalAddOnIdList;

    }

    @Getter
    @Setter
    public static class UpdateItem {
        private String parentCategoryId;
        private String subCategoryId;

        private String title;
        private String description;
        private VegNonVegEnum vegNonVeg = VegNonVegEnum.None;
        private String itemImageUrl;

        private String sticker;

        private double maximumRetailPrice;
        private double salePrice;

        private List<String> requiredAddOnIdList;
        private List<String> optionalAddOnIdList;

    }

    @Setter
    @Getter
    public static class GetList extends _BasePageRequest {
        private String search;
    }

    @Setter
    @Getter
    public static class DetailItem extends _BasicDto {
        private String parentCategoryId;
        private String subCategoryId;

        private String title;
        private String description;
        private VegNonVegEnum vegNonVeg;
        private String itemImageUrl;

        private String sticker;

        private double maximumRetailPrice;
        private double salePrice;

        private List<String> requiredAddOnIdList;
        private List<String> optionalAddOnIdList;
    }

}
