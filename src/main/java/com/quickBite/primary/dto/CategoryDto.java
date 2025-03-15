package com.quickBite.primary.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {

    @Getter
    @Setter
    public static class CreateCategory {
        private String restaurantId;
        private String parentCategoryId;
        private String title;
        private String description;
        private Long sortOrder;
        private String categoryIconUrl;
    }

    @Getter
    @Setter
    public static class UpdateCategory {
        private String parentCategoryId;
        private String title;
        private String description;
        private Long sortOrder;
        private String categoryIconUrl;
    }

    @Setter
    @Getter
    public static class GetList extends _BasePageRequest {
        private String search;
    }

    @Setter
    @Getter
    public static class DetailCategory extends _BasicDto {
        private String restaurantId;
        private String parentCategoryId;
        private String title;
        private String description;
        private Long sortOrder;
        private String categoryIconUrl;
    }

}
