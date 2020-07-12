package com.palod.commerce.domain.user.mapper;

import org.mapstruct.Mapper;

import com.palod.commerce.domain.user.dto.UserDto;
import com.palod.commerce.domain.user.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

	UserDto userToUserDto(User user);

	User userDtoToUser(UserDto userDto);
}
