package com.palod.commerce.domain.product.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.palod.commerce.domain.product.dto.ProductImageDto;
import com.palod.commerce.domain.product.entity.ProductImage;

@Mapper(componentModel = "spring")
public interface ProductImageMapper {

	ProductImageDto productImageToProductImageDto(ProductImage productImage);

	ProductImage productImageDtoToProductImage(ProductImageDto productImageDto);

	List<ProductImageDto> toProductImageDtoList(List<ProductImage> productImageList);

}
