package com.quickBite.primary.mapper;

import com.quickBite.primary.dto.AuthDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AuthMapper {
    AuthMapper MAPPER = Mappers.getMapper(AuthMapper.class);

//    AuthDto.UserDetails mapToUserDetails(_BaseUser user);

}
