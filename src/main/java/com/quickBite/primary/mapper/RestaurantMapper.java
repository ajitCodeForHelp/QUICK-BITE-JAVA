package com.quickBite.primary.mapper;

import com.quickBite.primary.dto.RestaurantDto;
import com.quickBite.primary.pojo.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {
    RestaurantMapper MAPPER = Mappers.getMapper(RestaurantMapper.class);

    Restaurant mapToPojo(RestaurantDto.CreateRestaurant create);

    @Mapping(expression = "java(com.quickBite.utils.DateUtils.getTimeStamp(create.getCreatedAt()))", target = "createdAtTimeStamp")
    @Mapping(expression = "java(com.quickBite.utils.DateUtils.getTimeStamp(create.getModifiedAt()))", target = "modifiedAtTimeStamp")
    RestaurantDto.DetailRestaurant mapToDetailDto(Restaurant create);

    Restaurant mapToPojo(@MappingTarget Restaurant restaurant, RestaurantDto.UpdateRestaurant update);


}
