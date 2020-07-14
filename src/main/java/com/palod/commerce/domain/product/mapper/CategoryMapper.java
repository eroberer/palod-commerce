package com.palod.commerce.domain.product.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.palod.commerce.domain.product.dto.CategoryDto;
import com.palod.commerce.domain.product.entity.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

	CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

	CategoryDto toCategoryDto(Category category);

	Category toCategory(CategoryDto categoryDto);

	List<CategoryDto> toCategoryDtoList(List<Category> categoryList);

	@Mapping(target = "id", ignore = true)
	Category updateCategory(CategoryDto categoryDto, @MappingTarget Category category);
}
