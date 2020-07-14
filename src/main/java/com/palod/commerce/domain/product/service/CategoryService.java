package com.palod.commerce.domain.product.service;

import java.util.List;

import com.palod.commerce.domain.product.dto.CategoryDto;

public interface CategoryService {

	List<CategoryDto> findAll();

	CategoryDto findById(Long id);

	void saveCategory(CategoryDto categoryDto);

	void updateCategory(Long id, CategoryDto categoryDto);

	void deleteCategory(Long id);
}
