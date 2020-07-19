package com.palod.commerce.api.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiErrorResponse extends BaseResponse {

	private List<String> errorList;

}
