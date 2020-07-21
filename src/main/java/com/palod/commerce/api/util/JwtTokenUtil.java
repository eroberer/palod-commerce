package com.palod.commerce.api.util;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.palod.commerce.domain.user.dto.RoleDto;
import com.palod.commerce.domain.user.service.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil {

	private String secretKey = "s3cr3ttt";
	private long expireTime = 3_600_000;

	private final UserService userService;

	public String createToken(String username, Set<RoleDto> roles) {

		Claims claims = Jwts.claims().setSubject(username);
		claims.put("roles",
				roles.stream().map(role -> new SimpleGrantedAuthority(role.getName()))
				.collect(Collectors.toList()));

		Date now = new Date();

		return Jwts.builder().setClaims(claims)
				.setIssuedAt(now).setExpiration(new Date(now.getTime() + expireTime))
				.signWith(SignatureAlgorithm.HS256, secretKey).compact();
	}

	public Authentication getAuthentication(String token) {
		UserDetails userDetails = userService.loadUserByUsername(getUsername(token));
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	public String getUsername(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
	}

	public String resolveToken(HttpServletRequest req) {
		String bearerToken = req.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}

	public void validateToken(String token) throws Exception {
		Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
	}

}
