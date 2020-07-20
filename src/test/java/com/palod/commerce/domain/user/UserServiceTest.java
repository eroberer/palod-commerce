package com.palod.commerce.domain.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.palod.commerce.api.exception.ApiException;
import com.palod.commerce.domain.user.dto.UserDto;
import com.palod.commerce.domain.user.entity.User;
import com.palod.commerce.domain.user.repository.UserRepository;
import com.palod.commerce.domain.user.service.UserService;

@SpringBootTest
public class UserServiceTest {

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@MockBean
	private UserRepository userRepository;

	@Test
	public void givenExistEmail_whenFindByEmail_thenReturnUser() {
		Long userId = 1L;
		String email = "test@test";
		String password = "test";

		Mockito.when(userRepository.findByEmail(email))
				.thenReturn(Optional.of(TestUtil.createDummyUser(userId, email, password, "basic_role")));

		UserDto userDto = userService.findByEmail(email);

		assertEquals(userId, userDto.getId());
		assertEquals(email, userDto.getEmail());
		Mockito.verify(userRepository).findByEmail(email);
	}

	@Test
	public void givenExistEmail_whenLoadUserByUsername_thenReturnSpringUser() {
		Long userId = 1L;
		String email = "test@test";
		String password = "test";
		String roleName = "BASIC";

		Mockito.when(userRepository.findByEmail(email))
				.thenReturn(Optional.of(TestUtil.createDummyUser(userId, email, password, roleName)));

		UserDetails userDetails = userService.loadUserByUsername(email);

		assertEquals(email, userDetails.getUsername());
		assertEquals(password, userDetails.getPassword());
		assertEquals(1, userDetails.getAuthorities().size());
		assertTrue(userDetails.getAuthorities().contains(new SimpleGrantedAuthority(roleName)));

		Mockito.verify(userRepository).findByEmail(email);
	}

	@Test
	public void givenCorrectEmailAndPassword_whenLogin_thenReturnUser() throws ApiException {
		Long userId = 1L;
		String email = "test@test";
		String password = "test";
		String roleName = "BASIC";

		Mockito.when(userRepository.findByEmail(email)).thenReturn(
				Optional.of(TestUtil.createDummyUser(userId, email, passwordEncoder.encode(password), roleName)));

		UserDto userDto = userService.login(email, password);

		assertEquals(userId, userDto.getId());
		assertEquals(email, userDto.getEmail());
		Mockito.verify(userRepository).findByEmail(email);
	}

	@Test
	public void givenCorrectEmailAndWrongPassword_whenLogin_thenThrowsApiException() throws ApiException {
		Long userId = 1L;
		String email = "test@test";
		String password = "test";
		String roleName = "BASIC";

		Mockito.when(userRepository.findByEmail(email)).thenReturn(
				Optional.of(TestUtil.createDummyUser(userId, email, passwordEncoder.encode(password), roleName)));

		assertThrows(ApiException.class, () -> {
			userService.login(email, password + "wrong");
		});
	}

	@Test
	public void givenUserDto_whenRegister_thenSaveUser() throws ApiException {
		String email = "test@test";
		String password = "test";
		String roleName = "BASIC";

		User user = TestUtil.createDummyUser(null, email, passwordEncoder.encode(password), roleName);

		Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

		UserDto userDto = TestUtil.createDummyUserDto(null, email, password);
		userService.register(userDto);

		user = TestUtil.createDummyUser(null, email, passwordEncoder.encode(password), roleName);
		Mockito.verify(userRepository).findByEmail(email);
		Mockito.verify(userRepository).save(Mockito.any(User.class));
	}

	@Test
	public void givenExistEmailInUserDto_whenRegister_thenThrowApiException() throws ApiException {
		String email = "test@test";
		String password = "test";
		String roleName = "BASIC";

		User user = TestUtil.createDummyUser(null, email, passwordEncoder.encode(password), roleName);

		Mockito.when(userRepository.findByEmail(Mockito.any(String.class))).thenReturn(Optional.of(user));
		Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

		UserDto userDto = TestUtil.createDummyUserDto(null, email, password);

		assertThrows(ApiException.class, () -> {
			userService.register(userDto);
		});

		Mockito.verify(userRepository).findByEmail(email);
		Mockito.verifyNoMoreInteractions(userRepository);
	}

}
