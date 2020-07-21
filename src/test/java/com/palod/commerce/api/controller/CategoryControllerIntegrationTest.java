package com.palod.commerce.api.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.palod.commerce.TestUtil;
import com.palod.commerce.domain.product.dto.CategoryDto;
import com.palod.commerce.domain.product.entity.Category;
import com.palod.commerce.domain.product.mapper.CategoryMapper;
import com.palod.commerce.domain.product.repository.CategoryRepository;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class CategoryControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private CategoryMapper categoryMapper;

	@Autowired
	private CategoryRepository categoryRepository;

	@Test
	public void whenFindAll_thenReturnCategoryList() throws JsonProcessingException, Exception {
		Category category = TestUtil.createDummyCategory(1L, "test category");
		Category category2 = TestUtil.createDummyCategory(2L, "test category");
		List<Category> categoryList = Lists.newArrayList(category, category2);
		categoryList = categoryRepository.saveAll(categoryList);

		mockMvc.perform(get("/api/category/").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content()
						.json(objectMapper.writeValueAsString(categoryMapper.toCategoryDtoList(categoryList))));
	}

	@Test
	public void whenFindById_thenReturnCategory() throws JsonProcessingException, Exception {
		Category category = TestUtil.createDummyCategory(1L, "test category");
		category = categoryRepository.save(category);

		mockMvc.perform(get("/api/category/{categoryId}/", 1L).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().json(objectMapper.writeValueAsString(categoryMapper.toCategoryDto(category))));
	}

	@Test
	public void givenValidCategory_whenSaveCategory_thenReturnHttpForbidden()
			throws JsonProcessingException, Exception {
		CategoryDto category = TestUtil.createDummyCategoryDto(21L, "test category");

		mockMvc.perform(post("/api/category/").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(category))).andExpect(status().isForbidden());
	}

	@Test
	public void givenValidCategory_whenAdminInvokeSaveCategory_thenReturnHttpOk() throws JsonProcessingException, Exception {
		CategoryDto category = TestUtil.createDummyCategoryDto(1L, "test category");

		mockMvc.perform(post("/api/category/").contentType(MediaType.APPLICATION_JSON)
				.with(user("admin").roles("ADMIN")).content(objectMapper.writeValueAsString(category)))
				.andExpect(status().isOk());
	}

}
