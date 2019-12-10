package com.cisneiros.prova;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
		User userFound = userService.findUserById(2L);
		
		assertEquals(user.getId(), userFound.getId());
		assertEquals(user.getUsername(), userFound.getUsername());
		assertEquals(user.getName(), userFound.getName());
	}
	
	@Test
	void updateUser() {
		UserService userService = new UserService(userRepository);
		User user = new User("vcisneiros", "123", "Victor");
		user = userService.save(user);
		user.setName("Cisneiros");
		user.setPassword("123456");
		userService.update(user);
		
		User storegedUser = userService.findUserById(user.getId());
		assertEquals(storegedUser.getName(), "Cisneiros");
		assertEquals(storegedUser.getPassword(), "123456");
		
	}
	
	@Test
	void deleteUser() {
		UserService userService = new UserService(userRepository);
		User user1 = new User("vcisneiros", "123", "Victor");
		User user2 = new User("maria", "123", "Maria");
		userService.save(user1);
		userService.save(user2);
		
		userService.delete(user2);
		assertEquals(1, userService.findAll().size());
	}
	
	@AfterEach
	void clearData() {
		userRepository.deleteAll();
	}
}
