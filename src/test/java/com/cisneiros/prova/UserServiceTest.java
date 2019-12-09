package com.cisneiros.prova;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

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
		
		assertEquals(userBeforeSave.getNome(), userAfterSave.getNome());
		assertEquals(userBeforeSave.getUsername(), userAfterSave.getUsername());
		assertEquals(userBeforeSave.getPassword(), userAfterSave.getPassword());
		
	}
}
