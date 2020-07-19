package com.palod.commerce.domain.user.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.palod.commerce.api.exception.ApiException;
import com.palod.commerce.domain.user.dto.UserDto;

public interface UserService extends UserDetailsService {

	UserDto findByEmail(String email);

	UserDto login(String username, String password) throws ApiException;

	void register(UserDto userDto) throws ApiException;
}
