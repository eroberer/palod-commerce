package com.palod.commerce.api.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.palod.commerce.api.constant.ApiUrl;
import com.palod.commerce.api.exception.ApiException;
import com.palod.commerce.api.request.LoginRequest;
import com.palod.commerce.api.response.LoginResponse;
import com.palod.commerce.api.util.JwtTokenUtil;
import com.palod.commerce.domain.user.dto.UserDto;
import com.palod.commerce.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(ApiUrl.USER_URL)
public class UserController {

	private final UserService userService;
	private final JwtTokenUtil jwtTokenUtil;

	@PostMapping("/login")
	public LoginResponse login(@RequestBody(required = true) @Valid LoginRequest loginRequest) throws ApiException {
		UserDto userDto = userService.login(loginRequest.getEmail(), loginRequest.getPassword());
		
		LoginResponse loginResponse = new LoginResponse();
		loginResponse.setToken(jwtTokenUtil.createToken(userDto.getEmail(), userDto.getRoles()));
		loginResponse.setStatus(HttpStatus.OK.value());
		
		return loginResponse;
	}

	@PostMapping("/register")
	public void register(@RequestBody(required = true) @Valid UserDto userDto) throws ApiException {
		userService.register(userDto);
	}

}
