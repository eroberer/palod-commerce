package com.palod.commerce;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.palod.commerce.domain.product.dto.CategoryDto;
import com.palod.commerce.domain.product.dto.ProductDto;
import com.palod.commerce.domain.product.entity.Category;
import com.palod.commerce.domain.product.entity.Product;
import com.palod.commerce.domain.user.dto.UserDto;
import com.palod.commerce.domain.user.entity.Role;
import com.palod.commerce.domain.user.entity.User;

public class TestUtil {

	public static Product createDummyProduct(Long id, String name) {
		Product product = new Product();
		product.setId(id);
		product.setName(name);
		product.setAmount(new BigDecimal(25));
		product.setQuantity(1L);
		return product;
	}

	public static ProductDto createDummyProductDto(Long id, String name) {
		ProductDto product = new ProductDto();
		product.setId(id);
		product.setName(name);
		return product;
	}

	public static Category createDummyCategory(Long id, String name) {
		Category category = new Category();
		category.setId(id);
		category.setName(name);
		return category;
	}

	public static CategoryDto createDummyCategoryDto(Long id, String name) {
		CategoryDto category = new CategoryDto();
		category.setId(id);
		category.setName(name);
		category.setDescription("test desc");
		return category;
	}

	public static User createDummyUser(Long id, String email, String password, String roleName) {
		User user = new User();
		user.setId(id);
		user.setFullName("test username");
		user.setEmail(email);
		user.setPassword(password);

		Set<Role> roles = new HashSet<>();
		roles.add(createDummyRole(roleName));

		user.setRoles(roles);
		return user;
	}

	public static Role createDummyRole(String roleName) {
		Role role = new Role();
		role.setName(roleName);
		return role;
	}

	public static UserDto createDummyUserDto(Long id, String email, String password) {
		UserDto user = new UserDto();
		user.setId(id);
		user.setEmail(email);
		user.setPassword(password);
		return user;
	}
}
