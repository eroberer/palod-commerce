package com.palod.commerce;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.palod.commerce.domain.user.entity.Role;
import com.palod.commerce.domain.user.entity.User;
import com.palod.commerce.domain.user.repository.RoleRepository;
import com.palod.commerce.domain.user.repository.UserRepository;

@Configuration
public class InitData implements ApplicationRunner {
	
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public void run(ApplicationArguments args) {
		userRepository.deleteAll();
		roleRepository.deleteAll();

		Role adminRole = new Role();
		adminRole.setName("ROLE_ADMIN");
		roleRepository.save(adminRole);

		Role basicRole = new Role();
		basicRole.setName("ROLE_BASIC");
		roleRepository.save(basicRole);

		User adminUser = new User();
		adminUser.setEmail("admin@admin");
		adminUser.setPassword(new BCryptPasswordEncoder().encode("password"));
		Set<Role> roles = new HashSet<>();
		roles.add(adminRole);
		adminUser.setRoles(roles);
		userRepository.save(adminUser);
	}
}
