package com.palod.commerce.api.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

	@Email
	@NotNull
	private String email;

	@NotNull
	@Size(min = 8, max = 32)
	private String password;
}
