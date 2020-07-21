package com.palod.commerce.api.filter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import com.google.common.collect.Lists;
import com.palod.commerce.api.util.JwtTokenUtil;

@SpringBootTest
public class JwtTokenFilterTest {

	@Autowired
	private JwtTokenFilter jwtTokenFilter;

	@MockBean
	private JwtTokenUtil jwtTokenUtil;

	@Test
	public void givenValidToken_whenDoFilterInternal_thenCreateSessionContext() throws ServletException, IOException {
		String token = "test_token";
		Authentication auth = createDummyAuthentication("testMail", "P", "R");

		Mockito.when(jwtTokenUtil.resolveToken(Mockito.any(HttpServletRequest.class))).thenReturn(token);
		Mockito.when(jwtTokenUtil.getAuthentication(token)).thenReturn(auth);

		SecurityContextHolder.clearContext();
		assertEquals(null, SecurityContextHolder.getContext().getAuthentication());

		jwtTokenFilter.doFilter(Mockito.mock(HttpServletRequest.class), Mockito.mock(HttpServletResponse.class),
				Mockito.mock(FilterChain.class));

		UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		assertEquals("testMail", user.getUsername());
	}

	@Test
	public void givenInvalidToken_whenDoFilterInternal_thenDoesNotCreateSessionContext() throws Exception {
		String token = "test_token";

		Mockito.when(jwtTokenUtil.resolveToken(Mockito.any(HttpServletRequest.class))).thenReturn(token);
		Mockito.doThrow(new Exception()).when(jwtTokenUtil).validateToken(token);

		jwtTokenFilter.doFilter(Mockito.mock(HttpServletRequest.class), Mockito.mock(HttpServletResponse.class),
				Mockito.mock(FilterChain.class));

		assertEquals(null, SecurityContextHolder.getContext().getAuthentication());
	}

	private Authentication createDummyAuthentication(String email, String password, String role) {
		UserDetails userDetail = User.withUsername(email).password(password)
				.authorities(Lists.newArrayList(new SimpleGrantedAuthority(role))).build();

		return new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
	}
}
