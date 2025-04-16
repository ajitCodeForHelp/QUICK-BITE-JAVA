package com.quickBite.primary.mapper;

import com.quickBite.bean.KeyValueDto;
import com.quickBite.primary.dto.TaxDto;
import com.quickBite.primary.pojo.Tax;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TaxMapper {
    TaxMapper MAPPER = Mappers.getMapper(TaxMapper.class);

    Tax mapToPojo(TaxDto.CreateTax create);

    @Mapping(expression = "java(com.quickBite.utils.DateUtils.getTimeStamp(create.getCreatedAt()))", target = "createdAtTimeStamp")
    @Mapping(expression = "java(com.quickBite.utils.DateUtils.getTimeStamp(create.getModifiedAt()))", target = "modifiedAtTimeStamp")
    @Mapping(expression = "java(com.quickBite.utils.TextUtils.toStringId(create.getVendorId()))", target = "vendorId")
    TaxDto.DetailTax mapToDetailDto(Tax create);

    Tax mapToPojo(@MappingTarget Tax tax, TaxDto.UpdateTax update);

    @Mapping(expression = "java(tax.getId())", target = "key")
    @Mapping(expression = "java(tax.getTitle())", target = "value")
    @Mapping(expression = "java(tax.getTitle())", target = "label")
    KeyValueDto mapToKeyValueDto(Tax tax);
}
