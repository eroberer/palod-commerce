package com.palod.commerce.api.response;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseResponse {

	private LocalDateTime timestamp = LocalDateTime.now();
	private int status;
}
