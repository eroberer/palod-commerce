package com.palod.commerce.domain.user;

import java.util.HashSet;
import java.util.Set;

import com.palod.commerce.domain.user.dto.UserDto;
import com.palod.commerce.domain.user.entity.Role;
import com.palod.commerce.domain.user.entity.User;

public class TestUtil {

	public static User createDummyUser(Long id, String email, String password, String roleName) {
		User user = new User();
		user.setId(id);
		user.setFullName("test username");
		user.setEmail(email);
		user.setPassword(password);

		Set<Role> roles = new HashSet<>();
		roles.add(createDummyRole(roleName));

		user.setRoles(roles);
		return user;
	}

	public static Role createDummyRole(String roleName) {
		Role role = new Role();
		role.setName(roleName);
		return role;
	}
	
	public static UserDto createDummyUserDto(Long id, String email, String password) {
		UserDto user = new UserDto();
		user.setId(id);
		user.setEmail(email);
		user.setPassword(password);
		return user;
	}
	
}
