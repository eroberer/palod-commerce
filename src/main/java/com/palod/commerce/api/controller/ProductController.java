package com.palod.commerce.api.controller;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.palod.commerce.api.constant.ApiUrl;
import com.palod.commerce.api.exception.ApiException;
import com.palod.commerce.api.util.AuthUtil;
import com.palod.commerce.domain.product.dto.ProductDto;
import com.palod.commerce.domain.product.service.ProductService;

import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(ApiUrl.PRODUCT_URL)
public class ProductController {

	private final AuthUtil authUtil;
	private final ProductService productService;

	@GetMapping("/{productId}")
	public ProductDto findById(@PathVariable(required = true) Long productId) {
		return productService.findById(productId);
	}

	@PostMapping
	public void saveProduct(@RequestBody @Valid ProductDto productDto) {
		productDto.setCreatedBy(authUtil.getRegisteredUser());
		productService.saveProduct(productDto);
	}

	@PutMapping("/{productId}")
	public void updateProduct(@PathVariable(required = true) Long productId,
			@RequestBody(required = true) @Valid ProductDto productDto) throws ApiException {
		authUtil.checkProductOwnerOrHasAdminRole(productId);

		productDto.setCreatedBy(authUtil.getRegisteredUser());
		productService.updateProduct(productId, productDto);

	}

	@DeleteMapping("/{productId}")
	public void deleteProduct(@PathVariable(required = true) Long productId) throws ApiException {
		authUtil.checkProductOwnerOrHasAdminRole(productId);
		
		productService.deleteProduct(productId);
	}
}
