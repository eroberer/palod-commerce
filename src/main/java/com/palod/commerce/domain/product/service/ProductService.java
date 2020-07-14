package com.palod.commerce.domain.product.service;

import java.util.List;

import com.palod.commerce.domain.product.dto.ProductDto;

public interface ProductService {

	List<ProductDto> findProductListByCategoryId(Long categoryId);

	ProductDto findById(Long id);

	void saveProduct(ProductDto productDto);

	void updateProduct(Long id, ProductDto productDto);

	void deleteProduct(Long id);

}
