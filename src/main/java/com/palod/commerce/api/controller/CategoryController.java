package com.palod.commerce.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.palod.commerce.api.constant.ApiUrl;
import com.palod.commerce.domain.product.dto.CategoryDto;
import com.palod.commerce.domain.product.dto.ProductDto;
import com.palod.commerce.domain.product.service.CategoryService;
import com.palod.commerce.domain.product.service.ProductService;

import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(ApiUrl.CATEGORY_URL)
public class CategoryController {

	private final CategoryService categoryService;
	private final ProductService productSerivce;

	@GetMapping
	public List<CategoryDto> findAll() {
		return categoryService.findAll();
	}

	@GetMapping("/{categoryId}")
	public CategoryDto findById(@PathVariable(required = true) Long categoryId) {
		return categoryService.findById(categoryId);
	}

	@PostMapping
	public void saveCategory(@RequestBody(required = true) @Valid CategoryDto categoryDto) {
		categoryService.saveCategory(categoryDto);
	}

	@PutMapping("/{categoryId}")
	public void updateCategory(@PathVariable(required = true) Long categoryId,
			@RequestBody(required = true) @Valid CategoryDto categoryDto) {
		categoryService.updateCategory(categoryId, categoryDto);
	}

	@DeleteMapping("/{categoryId}")
	public void deleteCategory(@PathVariable(required = true) Long categoryId) {
		categoryService.deleteCategory(categoryId);
	}

	@GetMapping("/{categoryId}/product")
	public List<ProductDto> findProductListByCategoryId(@PathVariable(required = true) Long categoryId) {
		return productSerivce.findProductListByCategoryId(categoryId);
	}

}
