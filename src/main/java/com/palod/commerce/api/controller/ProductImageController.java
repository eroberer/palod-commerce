package com.palod.commerce.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.palod.commerce.api.constant.ApiUrl;
import com.palod.commerce.domain.product.service.ProductImageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiUrl.PRODUCT_IMAGES_URL)
public class ProductImageController {

	private final ProductImageService productImageService;

	@PostMapping("/{productId}")
	public ResponseEntity<Boolean> saveImage(@PathVariable(required = true) Long productId,
			@RequestParam("file") MultipartFile file) {
		try {
			productImageService.saveImage(productId, file);
			return ResponseEntity.ok().body(Boolean.TRUE);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(Boolean.FALSE);
		}
	}

	@DeleteMapping("/{productImageId}")
	public ResponseEntity<Boolean> deleteImage(@PathVariable(required = true) Long productImageId) {
		try {
			productImageService.deleteImage(productImageId);
			return ResponseEntity.ok().body(Boolean.TRUE);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(Boolean.FALSE);
		}
	}

}
