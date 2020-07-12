package com.palod.commerce.domain.product.dto;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.palod.commerce.domain.core.dto.BaseDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto extends BaseDto {

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
	private CategoryDto category;

	private List<ProductImageDto> productImageList;
}
