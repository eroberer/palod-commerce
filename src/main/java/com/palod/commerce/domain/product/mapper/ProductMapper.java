package com.palod.commerce.domain.product.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.palod.commerce.domain.product.dto.ProductDto;
import com.palod.commerce.domain.product.entity.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

	ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

	ProductDto toProductDto(Product product);

	Product toProduct(ProductDto productDto);

	List<ProductDto> toProductDtoList(List<Product> productList);
}
