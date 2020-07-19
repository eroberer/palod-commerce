package com.palod.commerce.domain.product.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface ProductImageService {

	void saveImage(Long productId, MultipartFile file) throws IOException;

	void deleteImage(Long productImageId) throws Exception;

}
