package com.palod.commerce.domain.product;

import com.palod.commerce.domain.product.entity.Category;
import com.palod.commerce.domain.product.entity.Product;

public class TestUtil {
	
	public static Product createDummyProduct(Long id, String name) {
		Product product = new Product();
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
}
