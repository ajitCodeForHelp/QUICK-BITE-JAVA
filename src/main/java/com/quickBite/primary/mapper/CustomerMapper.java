package com.quickBite.primary.mapper;

import com.quickBite.primary.dto.CustomerDto;
import com.quickBite.primary.pojo.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerMapper MAPPER = Mappers.getMapper(CustomerMapper.class);

    Customer mapToPojo(@MappingTarget Customer customer, CustomerDto.UpdateCustomer update);

    CustomerDto.DetailCustomer mapToDetailDto(Customer customer);
}
