package com.palod.commerce.domain.product;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import com.google.common.collect.Lists;
import com.palod.commerce.TestUtil;
import com.palod.commerce.domain.product.dto.CategoryDto;
import com.palod.commerce.domain.product.entity.Category;
import com.palod.commerce.domain.product.repository.CategoryRepository;
import com.palod.commerce.domain.product.service.CategoryService;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class CategoryServiceTest {

	@Autowired
	private CategoryService categoryService;

	@MockBean
	private CategoryRepository categoryRepository;

	@Test
	public void whenFindAll_ThenReturnAllCategoryList() {
		List<Category> categoryList = Lists.newArrayList(TestUtil.createDummyCategory(1L, "cat1"),
				TestUtil.createDummyCategory(2L, "cat2"));

		Mockito.when(categoryRepository.findAll()).thenReturn(categoryList);

		List<CategoryDto> findAll = categoryService.findAll();

		assertEquals(categoryList.size(), findAll.size());
		Mockito.verify(categoryRepository).findAll();
	}

	@Test
	public void givenExistCategoryId_whenfindById_ThenReturnCategory() {
		Long categoryId = 1L;
		String categoryName = "test category";

		Mockito.when(categoryRepository.findById(categoryId))
				.thenReturn(Optional.of(TestUtil.createDummyCategory(categoryId, categoryName)));

		CategoryDto categoryDto = categoryService.findById(categoryId);

		assertEquals(categoryId, categoryDto.getId());
		assertEquals(categoryName, categoryDto.getName());
		Mockito.verify(categoryRepository).findById(categoryId);
	}

	@Test
	public void givenCategoryDto_whenSave_thenSaveCategory() {
		Mockito.when(categoryRepository.save(Mockito.any(Category.class))).thenReturn(Mockito.any(Category.class));

		CategoryDto categoryDto = TestUtil.createDummyCategoryDto(null, "test category");
		categoryService.saveCategory(categoryDto);

		Mockito.verify(categoryRepository).save(Mockito.any(Category.class));
		Mockito.verifyNoMoreInteractions(categoryRepository);
	}
}
