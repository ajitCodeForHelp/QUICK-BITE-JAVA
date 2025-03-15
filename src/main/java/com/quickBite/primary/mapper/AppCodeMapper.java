package com.quickBite.primary.mapper;

import com.quickBite.primary.dto.AppCodeDto;
import com.quickBite.primary.pojo.AppCode;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AppCodeMapper {
    AppCodeMapper MAPPER = Mappers.getMapper(AppCodeMapper.class);

    @Mapping(expression = "java(com.quickBite.utils.TextUtils.toObjectId(create.getVendorId()))", target = "vendorId")
    @Mapping(expression = "java(com.quickBite.utils.TextUtils.toObjectId(create.getRestaurantId()))", target = "restaurantId")
    AppCode mapToPojo(AppCodeDto.CreateAppCode create);

    @Mapping(expression = "java(com.quickBite.utils.DateUtils.getTimeStamp(appCode.getCreatedAt()))", target = "createdAtTimeStamp")
    @Mapping(expression = "java(com.quickBite.utils.DateUtils.getTimeStamp(appCode.getModifiedAt()))", target = "modifiedAtTimeStamp")
    @Mapping(expression = "java(com.quickBite.utils.TextUtils.toStringId(appCode.getVendorId()))", target = "vendorId")
    @Mapping(expression = "java(com.quickBite.utils.TextUtils.toStringId(appCode.getRestaurantId()))", target = "restaurantId")
    AppCodeDto.DetailAppCode mapToDetailDto(AppCode appCode);

    @Mapping(expression = "java(com.quickBite.utils.TextUtils.toObjectId(update.getVendorId()))", target = "vendorId")
    @Mapping(expression = "java(com.quickBite.utils.TextUtils.toObjectId(update.getRestaurantId()))", target = "restaurantId")
    AppCode mapToPojo(@MappingTarget AppCode appCode, AppCodeDto.UpdateAppCode update);

}
