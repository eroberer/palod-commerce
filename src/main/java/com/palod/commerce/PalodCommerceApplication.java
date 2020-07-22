package com.palod.commerce;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.palod.commerce.domain.user.entity.Role;
import com.palod.commerce.domain.user.entity.User;
import com.palod.commerce.domain.user.repository.RoleRepository;
import com.palod.commerce.domain.user.repository.UserRepository;

@SpringBootApplication
public class PalodCommerceApplication implements ApplicationRunner {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(PalodCommerceApplication.class, args);
	}

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
		adminUser.setPassword(passwordEncoder.encode("password"));
		Set<Role> roles = new HashSet<>();
		roles.add(adminRole);
		adminUser.setRoles(roles);
		userRepository.save(adminUser);
	}

}
