package com.palod.commerce.domain.product.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.palod.commerce.domain.product.dto.ProductImageDto;
import com.palod.commerce.domain.product.entity.ProductImage;

@Mapper(componentModel = "spring")
public interface ProductImageMapper {

	ProductImageMapper INSTANCE = Mappers.getMapper(ProductImageMapper.class);

	ProductImageDto toProductImageDto(ProductImage productImage);

	ProductImage toProductImage(ProductImageDto productImageDto);

	List<ProductImageDto> toProductImageDtoList(List<ProductImage> productImageList);

}
