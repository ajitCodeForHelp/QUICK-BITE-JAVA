package com.quickBite.primary.mapper;

import com.quickBite.bean.KeyValueDto;
import com.quickBite.primary.dto.ItemDto;
import com.quickBite.primary.pojo.menu.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    ItemMapper MAPPER = Mappers.getMapper(ItemMapper.class);

    @Mapping(expression = "java(com.quickBite.utils.TextUtils.toObjectId(create.getParentCategoryId()))", target = "parentCategoryId")
    @Mapping(expression = "java(com.quickBite.utils.TextUtils.toObjectId(create.getSubCategoryId()))", target = "subCategoryId")
    @Mapping(expression = "java(com.quickBite.utils.TextUtils.toObjectIds(create.getRequiredAddOnIdList()))", target = "requiredAddOnIdList")
    @Mapping(expression = "java(com.quickBite.utils.TextUtils.toObjectIds(create.getOptionalAddOnIdList()))", target = "optionalAddOnIdList")
    Item mapToPojo(ItemDto.CreateItem create);

    @Mapping(expression = "java(com.quickBite.utils.DateUtils.getTimeStamp(item.getCreatedAt()))", target = "createdAtTimeStamp")
    @Mapping(expression = "java(com.quickBite.utils.DateUtils.getTimeStamp(item.getModifiedAt()))", target = "modifiedAtTimeStamp")
    @Mapping(expression = "java(com.quickBite.utils.TextUtils.toStringId(item.getParentCategoryId()))", target = "parentCategoryId")
    @Mapping(expression = "java(com.quickBite.utils.TextUtils.toStringId(item.getSubCategoryId()))", target = "subCategoryId")
    @Mapping(expression = "java(com.quickBite.utils.TextUtils.toStringIds(item.getRequiredAddOnIdList()))", target = "requiredAddOnIdList")
    @Mapping(expression = "java(com.quickBite.utils.TextUtils.toStringIds(item.getOptionalAddOnIdList()))", target = "optionalAddOnIdList")
    ItemDto.DetailItem mapToDetailDto(Item item);

    @Mapping(expression = "java(com.quickBite.utils.TextUtils.toObjectId(update.getParentCategoryId()))", target = "parentCategoryId")
    @Mapping(expression = "java(com.quickBite.utils.TextUtils.toObjectId(update.getSubCategoryId()))", target = "subCategoryId")
    @Mapping(expression = "java(com.quickBite.utils.TextUtils.toObjectIds(update.getRequiredAddOnIdList()))", target = "requiredAddOnIdList")
    @Mapping(expression = "java(com.quickBite.utils.TextUtils.toObjectIds(update.getOptionalAddOnIdList()))", target = "optionalAddOnIdList")
    Item mapToPojo(@MappingTarget Item item, ItemDto.UpdateItem update);

    @Mapping(expression = "java(item.getId())", target = "key")
    @Mapping(expression = "java(item.getTitle())", target = "value")
    @Mapping(expression = "java(item.getTitle())", target = "label")
    KeyValueDto mapToKeyValueDto(Item item);

}
