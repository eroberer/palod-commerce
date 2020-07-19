package com.palod.commerce.domain.user.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.palod.commerce.api.exception.ApiException;
import com.palod.commerce.domain.user.dto.UserDto;
import com.palod.commerce.domain.user.entity.Role;
import com.palod.commerce.domain.user.entity.User;
import com.palod.commerce.domain.user.mapper.UserMapper;
import com.palod.commerce.domain.user.repository.RoleRepository;
import com.palod.commerce.domain.user.repository.UserRepository;
import com.palod.commerce.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserMapper userMapper;
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public UserDto findByEmail(String email) {
		return userMapper.toUserDto(userRepository.findByEmail(email).orElse(null));
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));

		List<SimpleGrantedAuthority> roleList = user.getRoles().stream()
				.map(role -> (new SimpleGrantedAuthority(role.getName()))).collect(Collectors.toList());

		return org.springframework.security.core.userdetails.User.withUsername(user.getEmail())
				.password(user.getPassword()).authorities(roleList).build();
	}

	@Override
	public UserDto login(String username, String password) throws ApiException {
		User user = userRepository.findByEmail(username).orElseThrow(() -> new ApiException(username + " not found!"));

		if (passwordEncoder.matches(password, user.getPassword())) {
			return userMapper.toUserDto(user);
		}

		throw new ApiException("invalid username or password");
	}

	@Override
	public void register(UserDto userDto) throws ApiException {
		if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
			throw new ApiException("exist user");
		}

		User user = userMapper.toUser(userDto);
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		user.setRoles(createBasicRole());

		userRepository.save(user);
	}

	private Set<Role> createBasicRole() {
		Set<Role> basicRole = new HashSet<>();
		basicRole.add(roleRepository.findByName("ROLE_BASIC").orElse(null));
		return basicRole;
	}
}
