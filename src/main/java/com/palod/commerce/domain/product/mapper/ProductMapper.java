package com.palod.commerce.domain.product.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.palod.commerce.domain.product.dto.ProductDto;
import com.palod.commerce.domain.product.entity.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

	ProductDto productToProductDto(Product product);

	Product productDtoToProduct(ProductDto productDto);

	List<ProductDto> toProductDtoList(List<Product> productList);
}
