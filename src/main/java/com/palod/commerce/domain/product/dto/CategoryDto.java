package com.palod.commerce.domain.product.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.palod.commerce.domain.core.dto.BaseDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto extends BaseDto {

	@NotNull
	@Size(max = 100)
	private String name;

	@NotNull
	@Size(max = 100)
	private String description;
}
