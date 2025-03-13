package com.quickBite.primary.mapper;

import com.quickBite.primary.dto.CouponCodeDto;
import com.quickBite.primary.pojo.CouponCode;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CouponCodeMapper {
    CouponCodeMapper MAPPER = Mappers.getMapper(CouponCodeMapper.class);

    CouponCode mapToPojo(CouponCodeDto.CreateCouponCode create);

    @Mapping(expression = "java(com.quickBite.utils.DateUtils.getTimeStamp(create.getCreatedAt()))", target = "createdAtTimeStamp")
    @Mapping(expression = "java(com.quickBite.utils.DateUtils.getTimeStamp(create.getModifiedAt()))", target = "modifiedAtTimeStamp")
    CouponCodeDto.DetailCouponCode mapToDetailDto(CouponCode create);

    CouponCode mapToPojo(@MappingTarget CouponCode banner, CouponCodeDto.UpdateCouponCode update);


}
