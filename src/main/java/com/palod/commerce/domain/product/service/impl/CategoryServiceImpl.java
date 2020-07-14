package com.palod.commerce.domain.product.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.palod.commerce.domain.product.dto.CategoryDto;
import com.palod.commerce.domain.product.entity.Category;
import com.palod.commerce.domain.product.mapper.CategoryMapper;
import com.palod.commerce.domain.product.repository.CategoryRepository;
import com.palod.commerce.domain.product.service.CategoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository categoryRepository;
	private final CategoryMapper categoryMapper;

	@Override
	public List<CategoryDto> findAll() {
		return categoryMapper.toCategoryDtoList(categoryRepository.findAll());
	}

	@Override
	public CategoryDto findById(Long id) {
		Optional<Category> category = categoryRepository.findById(id);

		if (category.isPresent()) {
			return categoryMapper.toCategoryDto(category.get());
		}

		return null;
	}

	@Override
	public void saveCategory(CategoryDto categoryDto) {
		categoryRepository.save(categoryMapper.toCategory(categoryDto));
	}

	@Override
	public void updateCategory(Long id, CategoryDto categoryDto) {
		Optional<Category> category = categoryRepository.findById(id);

		if (category.isPresent()) {
			categoryMapper.updateCategory(categoryDto, category.get());
			categoryRepository.save(category.get());
		}
	}

	@Override
	public void deleteCategory(Long id) {
		categoryRepository.findById(id).ifPresent(category -> categoryRepository.delete(category));
	}

}
