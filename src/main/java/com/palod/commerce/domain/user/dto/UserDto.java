package com.palod.commerce.domain.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.palod.commerce.domain.core.dto.BaseDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto extends BaseDto {

	@NotNull
	@Size(min = 1, max = 255)
	private String fullName;

	@Email
	@NotNull
	private String email;

	@NotNull
	@Size(min = 8, max = 255)
	private String password;
}
