package com.palod.commerce.domain.product;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.google.common.collect.Lists;
import com.palod.commerce.TestUtil;
import com.palod.commerce.domain.product.dto.ProductDto;
import com.palod.commerce.domain.product.entity.Product;
import com.palod.commerce.domain.product.repository.ProductRepository;
import com.palod.commerce.domain.product.service.ProductService;

@SpringBootTest
public class ProductServiceTest {

	@Autowired
	private ProductService productService;

	@MockBean
	private ProductRepository productRepository;

	@Test
	public void givenCategoryId_whenFindProductListByCategoryId_ThenReturnProductList() {
		Long categoryId = 1L;
		List<Product> productList = Lists.newArrayList(TestUtil.createDummyProduct(1L, "p1"),
				TestUtil.createDummyProduct(2L, "p2"));

		Mockito.when(productRepository.findByCategoryId(categoryId)).thenReturn(productList);

		List<ProductDto> findProductListByCategoryId = productService.findProductListByCategoryId(categoryId);

		assertEquals(productList.size(), findProductListByCategoryId.size());
		Mockito.verify(productRepository).findByCategoryId(categoryId);
	}

	@Test
	public void givenExistProductId_whenFinbdById_ThenReturnProduct() {
		Long productId = 1L;
		String name = "test product";

		Mockito.when(productRepository.findById(productId))
				.thenReturn(Optional.of(TestUtil.createDummyProduct(productId, name)));

		ProductDto productDto = productService.findById(productId);

		assertEquals(productId, productDto.getId());
		assertEquals(name, productDto.getName());
		Mockito.verify(productRepository).findById(productId);
	}
}
