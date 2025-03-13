package com.quickBite.primary.mapper;

import com.quickBite.primary.dto.RestaurantDto;
import com.quickBite.primary.pojo.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {
    RestaurantMapper MAPPER = Mappers.getMapper(RestaurantMapper.class);

//    Restaurant mapToPojo(RestaurantDto.CreateRestaurant create);

    RestaurantDto.DetailRestaurant mapToDetailDto(Restaurant create);

    Restaurant mapToPojo(@MappingTarget Restaurant restaurant, RestaurantDto.UpdateRestaurant update);


}
