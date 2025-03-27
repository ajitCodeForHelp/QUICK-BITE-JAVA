package com.quickBite.primary.mapper;

import com.quickBite.bean.KeyValueDto;
import com.quickBite.primary.dto.VendorDto;
import com.quickBite.primary.pojo.Tax;
import com.quickBite.primary.pojo.Vendor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface VendorMapper {
    VendorMapper MAPPER = Mappers.getMapper(VendorMapper.class);

    Vendor mapToPojo(VendorDto.CreateVendor create);

    @Mapping(expression = "java(com.quickBite.utils.DateUtils.getTimeStamp(create.getCreatedAt()))", target = "createdAtTimeStamp")
    @Mapping(expression = "java(com.quickBite.utils.DateUtils.getTimeStamp(create.getModifiedAt()))", target = "modifiedAtTimeStamp")
    VendorDto.DetailVendor mapToDetailDto(Vendor create);

    Vendor mapToPojo(@MappingTarget Vendor vendor, VendorDto.UpdateVendor update);
    @Mapping(expression = "java(vendor.getId())", target = "key")
    @Mapping(expression = "java(vendor.fullName())", target = "value")
    @Mapping(expression = "java(vendor.fullName())", target = "label")
    KeyValueDto mapToKeyValueDto(Vendor vendor);

}
