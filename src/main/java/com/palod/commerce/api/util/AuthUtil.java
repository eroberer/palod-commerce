package com.palod.commerce.api.util;

import java.util.Objects;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.palod.commerce.api.exception.ApiException;
import com.palod.commerce.domain.product.dto.ProductDto;
import com.palod.commerce.domain.product.service.ProductService;
import com.palod.commerce.domain.user.dto.UserDto;
import com.palod.commerce.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthUtil {

	private final UserService userService;
	private final ProductService productService;
	
	public UserDto getRegisteredUser() {
		return userService.findByEmail(getAuthentication().getName());
	}

	public void checkProductOwnerOrHasAdminRole(Long productId) throws ApiException {
		Optional<? extends GrantedAuthority> adminRole = getAuthentication().getAuthorities().stream()
				.filter((authory) -> "ROLE_ADMIN".equals(authory.getAuthority())).findAny();

		if (adminRole.isPresent()) {
			return;
		}
		
		ProductDto productDto = productService.findById(productId);
		if (!Objects.isNull(productDto) && productDto.getCreatedBy().getId().equals(getRegisteredUser().getId())) {
			return;
		}
		
		throw new ApiException("has not admin role or is not product owner", HttpStatus.FORBIDDEN.value());
	}
	
	private Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

}
