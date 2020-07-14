package com.palod.commerce.domain.product.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.palod.commerce.domain.product.dto.ProductDto;
import com.palod.commerce.domain.product.entity.Product;
import com.palod.commerce.domain.user.mapper.UserMapper;

@Mapper(componentModel = "spring", uses = { ProductImageMapper.class, CategoryMapper.class, UserMapper.class })
public interface ProductMapper {

	ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

	@Mapping(target = "userFullName", source = "createdBy.fullName")
	@Mapping(target = "categoryId", source = "category.id")
	ProductDto toProductDto(Product product);

	@InheritInverseConfiguration
	Product toProduct(ProductDto productDto);

	List<ProductDto> toProductDtoList(List<Product> productList);

	@Mapping(target = "id", ignore = true)
	Product updateProduct(ProductDto productDto, @MappingTarget Product product);
}
