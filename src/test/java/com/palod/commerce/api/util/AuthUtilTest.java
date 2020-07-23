package com.palod.commerce.api.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.TestPropertySource;

import com.google.common.collect.Lists;
import com.palod.commerce.TestUtil;
import com.palod.commerce.api.exception.ApiException;
import com.palod.commerce.domain.product.dto.ProductDto;
import com.palod.commerce.domain.product.service.ProductService;
import com.palod.commerce.domain.user.dto.UserDto;
import com.palod.commerce.domain.user.service.UserService;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class AuthUtilTest {

	@Autowired
	private AuthUtil authUtil;

	@MockBean
	private UserService userService;

	@MockBean
	private ProductService productService;

	@Test
	public void whenGetRegisteredUser_thenReturnSessionUser() {
		String email = "test@test";
		String password = "s3cre3t";

		createSessionContext(email, password, "ROLE_BASIC");

		Mockito.when(userService.findByEmail(Mockito.anyString()))
				.thenReturn(TestUtil.createDummyUserDto(1L, email, password));

		UserDto registeredUser = authUtil.getRegisteredUser();

		assertEquals(email, registeredUser.getEmail());
		Mockito.verify(userService).findByEmail(email);
	}

	@Test
	public void givenExistProductId_whenHasNotAdminRoleAndNotProductOwner_thenThrowApiException() throws ApiException {
		Long productId = 1L;

		createSessionContext("sessionUserMail", "t", "ROLE_BASIC");

		ProductDto productDto = TestUtil.createDummyProductDto(productId, null);
		UserDto productOwnerUser = TestUtil.createDummyUserDto(1L, "ownerMail", "t");
		productDto.setCreatedBy(productOwnerUser);

		UserDto sessionUser = TestUtil.createDummyUserDto(2L, "sessionUserMail", "t");

		Mockito.when(productService.findById(Mockito.anyLong())).thenReturn(productDto);
		Mockito.when(userService.findByEmail("sessionUserMail")).thenReturn(sessionUser);

		assertThrows(ApiException.class, () -> {
			authUtil.checkProductOwnerOrHasAdminRole(productId);
		});

		Mockito.verify(productService).findById(Mockito.anyLong());
		Mockito.verify(userService).findByEmail("sessionUserMail");
	}

	@Test
	public void givenExistProductId_whenUserIsProductOwner_thenDoesNotThrowException() throws ApiException {
		Long productId = 1L;

		createSessionContext("sessionUserMail", "t", "ROLE_BASIC");

		ProductDto productDto = TestUtil.createDummyProductDto(productId, null);
		UserDto productOwnerUser = TestUtil.createDummyUserDto(1L, "sessionUserMail", "t");
		productDto.setCreatedBy(productOwnerUser);

		Mockito.when(productService.findById(Mockito.anyLong())).thenReturn(productDto);
		Mockito.when(userService.findByEmail("sessionUserMail")).thenReturn(productOwnerUser);

		authUtil.checkProductOwnerOrHasAdminRole(productId);

		Mockito.verify(productService).findById(Mockito.anyLong());
		Mockito.verify(userService).findByEmail("sessionUserMail");
	}

	@Test
	public void givenExistProductId_whenHasAdminRole_thenDoesNotThrowException() throws ApiException {
		Long productId = 1L;

		createSessionContext("sessionUserMail", "t", "ROLE_ADMIN");

		ProductDto productDto = TestUtil.createDummyProductDto(productId, null);
		UserDto productOwnerUser = TestUtil.createDummyUserDto(1L, "ownerMail", "t");
		productDto.setCreatedBy(productOwnerUser);

		Mockito.when(productService.findById(Mockito.anyLong())).thenReturn(productDto);

		authUtil.checkProductOwnerOrHasAdminRole(productId);

		Mockito.verifyNoInteractions(productService);
	}

	private void createSessionContext(String email, String password, String role) {
		SecurityContextHolder.clearContext();

		UserDetails userDetail = User.withUsername(email).password(password)
				.authorities(Lists.newArrayList(new SimpleGrantedAuthority(role))).build();

		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities()));

	}
}
