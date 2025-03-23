package com.quickBite.primary.mapper;

import com.quickBite.bean.KeyValueDto;
import com.quickBite.primary.dto.ItemAddOnDto;
import com.quickBite.primary.pojo.menu.ItemAddOn;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ItemAddOnMapper {
    ItemAddOnMapper MAPPER = Mappers.getMapper(ItemAddOnMapper.class);

    @Mapping(expression = "java(com.quickBite.utils.TextUtils.toObjectId(create.getCategoryId()))", target = "categoryId")
    ItemAddOn mapToPojo(ItemAddOnDto.CreateItemAddOn create);

    @Mapping(expression = "java(com.quickBite.utils.DateUtils.getTimeStamp(itemAddOn.getCreatedAt()))", target = "createdAtTimeStamp")
    @Mapping(expression = "java(com.quickBite.utils.DateUtils.getTimeStamp(itemAddOn.getModifiedAt()))", target = "modifiedAtTimeStamp")
    @Mapping(expression = "java(com.quickBite.utils.TextUtils.toStringId(itemAddOn.getCategoryId()))", target = "categoryId")
    @Mapping(expression = "java(com.quickBite.utils.TextUtils.toStringId(itemAddOn.getRestaurantId()))", target = "restaurantId")
    ItemAddOnDto.DetailItemAddOn mapToDetailDto(ItemAddOn itemAddOn);

    @Mapping(expression = "java(com.quickBite.utils.TextUtils.toObjectId(update.getCategoryId()))", target = "categoryId")
    ItemAddOn mapToPojo(@MappingTarget ItemAddOn itemAddOn, ItemAddOnDto.UpdateItemAddOn update);

    @Mapping(expression = "java(itemAddOn.getId())", target = "key")
    @Mapping(expression = "java(itemAddOn.getTitle())", target = "value")
    @Mapping(expression = "java(itemAddOn.getTitle())", target = "label")
    KeyValueDto mapToKeyValueDto(ItemAddOn itemAddOn);

}
