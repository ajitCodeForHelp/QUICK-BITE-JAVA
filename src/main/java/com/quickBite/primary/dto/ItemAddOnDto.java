package com.quickBite.primary.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ItemAddOnDto {

    @Getter
    @Setter
    public static class CreateItemAddOn {
        private String restaurantId;
        private String categoryId;
        private String title;
        private double addOnPrice = 0.0;
        private Long sortOrder;
    }

    @Getter
    @Setter
    public static class UpdateItemAddOn {
        private String categoryId;
        private String title;
        private double addOnPrice = 0.0;
        private Long sortOrder;
    }

    @Setter
    @Getter
    public static class GetList extends _BasePageRequest {
        private String search;
    }

    @Setter
    @Getter
    public static class DetailItemAddOn extends _BasicDto {
        private String restaurantId;
        private String categoryId;
        private String title;
        private double addOnPrice = 0.0;
        private Long sortOrder;
    }

    @Getter
    @Setter
    public static class CategoryWiseItemAddOn {
        private List<String> selectedCategoryList;
    }

}
