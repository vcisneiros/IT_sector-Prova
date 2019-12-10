package com.cisneiros.prova;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class UserServiceTest {

	@Autowired
	private UserRepository userRepository;
	
	@Test
	void getAllUsers() {
		User userBeforeSave = new User("vcisneiros", "123", "Victor");
		userRepository.save(userBeforeSave);
		
		UserService userService = new UserService(userRepository);
		List<User> userList = userService.findAll();
		
		assertFalse(userList.isEmpty());
		
		User userAfterSave = userList.get(userList.size()-1);
		
		assertEquals(userBeforeSave.getName(), userAfterSave.getName());
		assertEquals(userBeforeSave.getUsername(), userAfterSave.getUsername());
		assertEquals(userBeforeSave.getPassword(), userAfterSave.getPassword());
		
	}
	
	@Test
	void saveUser() {
		UserService userService = new UserService(userRepository);
		User user = new User("vcisneiros", "123", "Victor");
		User savedUser = userService.save(user);
		assertFalse(userService.findAll().isEmpty());
		assertNotNull(savedUser.getCreatedAt());
		assertNotNull(savedUser.getUpdatedAt());
	}
	
	@Test
	void findUser() {
		UserService userService = new UserService(userRepository);
		User user = new User("vcisneiros", "123", "Victor");
		userService.save(user);
		User userFound = userService.findUserById(1L);
		
		assertEquals(user.getId(), userFound.getId());
		assertEquals(user.getUsername(), userFound.getUsername());
		assertEquals(user.getName(), userFound.getName());
	}
	
	@AfterEach
	void clearData() {
		userRepository.deleteAll();
	}
}
