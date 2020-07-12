package com.palod.commerce.domain.product.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.palod.commerce.domain.product.dto.CategoryDto;
import com.palod.commerce.domain.product.entity.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

	CategoryDto categoryToCategoryDto(Category category);

	Category categoryDtoToCategory(CategoryDto categoryDto);

	List<CategoryDto> toCategoryDtoList(List<Category> categoryList);
}
