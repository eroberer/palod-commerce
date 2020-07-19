package com.palod.commerce.api.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.palod.commerce.api.util.JwtTokenUtil;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			FilterChain filterChain) throws ServletException, IOException {
		String token = jwtTokenUtil.resolveToken(httpServletRequest);
		
		try {
			if (token != null) {
				jwtTokenUtil.validateToken(token);
				
				Authentication auth = jwtTokenUtil.getAuthentication(token);
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		} catch (Exception ex) {
			SecurityContextHolder.clearContext();
			
			httpServletResponse.sendError(HttpStatus.UNAUTHORIZED.value(), "invalid token");
			return;
		}

		filterChain.doFilter(httpServletRequest, httpServletResponse);
	}

}
