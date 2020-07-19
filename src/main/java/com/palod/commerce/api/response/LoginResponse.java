package com.palod.commerce.api.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse extends BaseResponse {

	private String token;
}
