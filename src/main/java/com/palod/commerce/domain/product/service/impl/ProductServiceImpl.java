package com.palod.commerce.domain.product.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.palod.commerce.domain.product.dto.ProductDto;
import com.palod.commerce.domain.product.entity.Product;
import com.palod.commerce.domain.product.mapper.ProductMapper;
import com.palod.commerce.domain.product.repository.ProductRepository;
import com.palod.commerce.domain.product.service.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;
	private final ProductMapper productMapper;

	@Override
	public List<ProductDto> findProductListByCategoryId(Long categoryId) {
		return productMapper.toProductDtoList(productRepository.findByCategoryId(categoryId));
	}

	@Override
	public ProductDto findById(Long id) {
		Optional<Product> product = productRepository.findById(id);

		if (product.isPresent()) {
			return productMapper.toProductDto(product.get());
		}

		return null;
	}

	@Override
	public void saveProduct(ProductDto productDto) {
		productRepository.save(productMapper.toProduct(productDto));

	}

	@Override
	public void updateProduct(Long id, ProductDto productDto) {
		Optional<Product> product = productRepository.findById(id);

		if (product.isPresent()) {
			productMapper.updateProduct(productDto, product.get());
			productRepository.save(product.get());
		}
	}

	@Override
	public void deleteProduct(Long id) {
		productRepository.findById(id).ifPresent(product -> productRepository.delete(product));
	}

}
