package com.palod.commerce.domain.product.dto;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.palod.commerce.domain.core.dto.BaseDto;
import com.palod.commerce.domain.user.dto.UserDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto extends BaseDto {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Size(max = 100)
	private String name;

	@Size(max = 100)
	private String description;

	@Min(1)
	@NotNull
	private BigDecimal amount;

	@Min(1)
	@NotNull
	private Long quantity;

	@NotNull
	private Long categoryId;

	private List<ProductImageDto> productImageList;

	private UserDto createdBy;
}
