package com.quickBite.primary.mapper;

import com.quickBite.primary.dto.AddressDto;
import com.quickBite.primary.pojo.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    AddressMapper MAPPER = Mappers.getMapper(AddressMapper.class);

//    @Mapping(expression = "java(address.getUuid())", target = "addressUuid")
//    Cart.CustomerAddressDetail mapToCartAddress(Address address);

    Address mapToPojo(AddressDto.SaveAddress saveAddress);

    Address mapToPojo(@MappingTarget Address address, AddressDto.UpdateAddress update);

    @Mapping(expression = "java(com.quickBite.utils.DateUtils.getTimeStamp(address.getCreatedAt()))", target = "createdAtTimeStamp")
    @Mapping(expression = "java(com.quickBite.utils.DateUtils.getTimeStamp(address.getModifiedAt()))", target = "modifiedAtTimeStamp")
    AddressDto.DetailAddress mapToDetailAddress(Address address);

}
