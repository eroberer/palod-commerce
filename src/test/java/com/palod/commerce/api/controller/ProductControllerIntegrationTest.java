package com.palod.commerce.api.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.Set;

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
import com.palod.commerce.domain.product.entity.Category;
import com.palod.commerce.domain.product.entity.Product;
import com.palod.commerce.domain.product.entity.ProductImage;
import com.palod.commerce.domain.product.mapper.ProductMapper;
import com.palod.commerce.domain.product.repository.CategoryRepository;
import com.palod.commerce.domain.product.repository.ProductImageRepository;
import com.palod.commerce.domain.product.repository.ProductRepository;
import com.palod.commerce.domain.user.entity.Role;
import com.palod.commerce.domain.user.entity.User;
import com.palod.commerce.domain.user.repository.RoleRepository;
import com.palod.commerce.domain.user.repository.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class ProductControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ProductMapper productMapper;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductImageRepository productImageRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Test
	public void givenProductId_whenFindById_thenReturnProduct() throws JsonProcessingException, Exception {
		Product product = createDummyProduct();

		mockMvc.perform(get("/api/product/{productId}/", 1L).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().json(objectMapper.writeValueAsString(productMapper.toProductDto(product))));
	}

	@Test
	public void givenProductIdAndProduct_whenNotOwnerInvokeUpdateProduct_thenThrowsApiException()
			throws JsonProcessingException, Exception {
		Product product = createDummyProduct();
		User notOwnerUser = createNotOwnerUser();

		mockMvc.perform(put("/api/product/{productId}/", 1L).contentType(MediaType.APPLICATION_JSON)
				.with(user(notOwnerUser.getEmail()).roles("BASIC"))
				.content(objectMapper.writeValueAsString(productMapper.toProductDto(product))))
				.andExpect(status().is4xxClientError());
	}

	@Test
	public void givenProductIdAndProduct_whenAdminInvokeUpdateProduct_thenUpdateProduct()
			throws JsonProcessingException, Exception {
		Product product = createDummyProduct();
		User adminUser = createNotOwnerUser();
		
		mockMvc.perform(put("/api/product/{productId}/", 1L).contentType(MediaType.APPLICATION_JSON)
				.with(user(adminUser.getEmail()).roles("ADMIN"))
				.content(objectMapper.writeValueAsString(productMapper.toProductDto(product))))
				.andExpect(status().isOk());
	}

	private Product createDummyProduct() {
		Category category = TestUtil.createDummyCategory(1L, "test category");
		category = categoryRepository.save(category);

		Role role = TestUtil.createDummyRole("BASIC");
		role = roleRepository.save(role);

		User user = TestUtil.createDummyUser(1L, "t", "t", "BASIC");
		Set<Role> roles = new HashSet<>();
		roles.add(role);
		user.setRoles(roles);
		user = userRepository.save(user);

		Product product = TestUtil.createDummyProduct(1L, "test product");
		product.setCategory(category);
		product.setCreatedBy(user);
		product = productRepository.save(product);

		ProductImage image = new ProductImage();
		image.setId(1L);
		image.setProduct(product);
		productImageRepository.save(image);

		product.setProductImageList(Lists.newArrayList(image));
		product = productRepository.save(product);

		return product;
	}

	public User createNotOwnerUser() {
		User notOwnerUser = TestUtil.createDummyUser(2L, "test@tset", "password", "BASIC");

		Role role = TestUtil.createDummyRole("BASIC");
		role = roleRepository.save(role);

		Set<Role> roles = new HashSet<>();
		roles.add(role);

		notOwnerUser.setRoles(roles);
		notOwnerUser = userRepository.save(notOwnerUser);

		return notOwnerUser;
	}
}
