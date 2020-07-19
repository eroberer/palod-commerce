package com.palod.commerce.domain.product.service.impl;

import java.io.IOException;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.palod.commerce.domain.core.service.FileManagementService;
import com.palod.commerce.domain.product.entity.Product;
import com.palod.commerce.domain.product.entity.ProductImage;
import com.palod.commerce.domain.product.repository.ProductImageRepository;
import com.palod.commerce.domain.product.repository.ProductRepository;
import com.palod.commerce.domain.product.service.ProductImageService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductImageServiceImpl implements ProductImageService {

	private final ProductRepository productRepository;
	private final FileManagementService fileManagementService;
	private final ProductImageRepository productImageRepository;

	@Override
	public void saveImage(Long productId, MultipartFile file) throws IOException {
		Optional<Product> product = productRepository.findById(productId);

		if (product.isPresent() && file != null) {
			String savedFileName = fileManagementService.saveFile(file.getOriginalFilename(), file.getInputStream());

			ProductImage productImage = new ProductImage();
			productImage.setProduct(product.get());
			productImage.setUrl(savedFileName);

			productImageRepository.save(productImage);
		}
	}

	@Override
	public void deleteImage(Long productImageId) throws Exception {
		Optional<ProductImage> productImage = productImageRepository.findById(productImageId);

		if (productImage.isPresent()) {
			fileManagementService.deleteFile(productImage.get().getUrl());

			productImageRepository.delete(productImage.get());
		}
	}
}
