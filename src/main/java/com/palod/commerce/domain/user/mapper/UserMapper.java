package com.palod.commerce.domain.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.palod.commerce.domain.user.dto.UserDto;
import com.palod.commerce.domain.user.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

	@Mapping(target = "password", ignore = true)
	UserDto toUserDto(User user);

	User toUser(UserDto userDto);
}
