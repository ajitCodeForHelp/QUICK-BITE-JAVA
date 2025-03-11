package com.quickBite.primary.mapper;

import com.quickBite.primary.dto.BannerDto;
import com.quickBite.primary.pojo.Banner;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BannerMapper {
    BannerMapper MAPPER = Mappers.getMapper(BannerMapper.class);

    Banner mapToPojo(BannerDto.CreateBanner create);

    BannerDto.DetailBanner mapToDetailDto(Banner create);

    Banner mapToPojo(@MappingTarget Banner banner, BannerDto.UpdateBanner update);


}
