package com.quickBite.primary.mapper;

import com.quickBite.primary.dto.VendorDto;
import com.quickBite.primary.pojo.Vendor;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface VendorMapper {
    VendorMapper MAPPER = Mappers.getMapper(VendorMapper.class);

    Vendor mapToPojo(VendorDto.CreateVendor create);

    VendorDto.DetailVendor mapToDetailDto(Vendor create);

    Vendor mapToPojo(@MappingTarget Vendor vendor, VendorDto.UpdateVendor update);


}
