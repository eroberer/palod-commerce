package com.palod.commerce.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.palod.commerce.api.constant.ApiUrl;
import com.palod.commerce.api.filter.JwtTokenFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtTokenFilter jwtTokenFilter;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();

		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.authorizeRequests()
			.antMatchers(HttpMethod.GET, ApiUrl.CATEGORY_URL + "/**").permitAll()
			.antMatchers(HttpMethod.POST, ApiUrl.CATEGORY_URL + "/**").hasRole("ADMIN")
			.antMatchers(HttpMethod.PUT, ApiUrl.CATEGORY_URL + "/**").hasRole("ADMIN")
			.antMatchers(HttpMethod.DELETE, ApiUrl.CATEGORY_URL + "/**").hasRole("ADMIN")
			
			.antMatchers(HttpMethod.GET, ApiUrl.PRODUCT_URL + "/**").permitAll()
			.antMatchers(ApiUrl.USER_URL + "/**").permitAll().anyRequest().authenticated();

		http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
	}


	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
