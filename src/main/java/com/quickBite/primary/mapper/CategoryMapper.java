package com.quickBite.primary.mapper;

import com.quickBite.bean.KeyValueDto;
import com.quickBite.primary.dto.CategoryDto;
import com.quickBite.primary.pojo.menu.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryMapper MAPPER = Mappers.getMapper(CategoryMapper.class);

    @Mapping(expression = "java(com.quickBite.utils.TextUtils.toObjectId(create.getParentCategoryId()))", target = "parentCategoryId")
    @Mapping(expression = "java(com.quickBite.utils.TextUtils.toObjectId(create.getRestaurantId()))", target = "restaurantId")
    Category mapToPojo(CategoryDto.CreateCategory create);

    @Mapping(expression = "java(com.quickBite.utils.DateUtils.getTimeStamp(category.getCreatedAt()))", target = "createdAtTimeStamp")
    @Mapping(expression = "java(com.quickBite.utils.DateUtils.getTimeStamp(category.getModifiedAt()))", target = "modifiedAtTimeStamp")
    @Mapping(expression = "java(com.quickBite.utils.TextUtils.toStringId(category.getParentCategoryId()))", target = "parentCategoryId")
    @Mapping(expression = "java(com.quickBite.utils.TextUtils.toStringId(category.getRestaurantId()))", target = "restaurantId")
    CategoryDto.DetailCategory mapToDetailDto(Category category);

    @Mapping(expression = "java(com.quickBite.utils.TextUtils.toObjectId(update.getParentCategoryId()))", target = "parentCategoryId")
    Category mapToPojo(@MappingTarget Category category, CategoryDto.UpdateCategory update);

    @Mapping(expression = "java(category.getId())", target = "key")
    @Mapping(expression = "java(category.getTitle())", target = "value")
    @Mapping(expression = "java(category.getTitle())", target = "label")
    KeyValueDto mapToKeyValueDto(Category category);


}
