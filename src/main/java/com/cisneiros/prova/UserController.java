package com.cisneiros.prova;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/utilizadores")
	ResponseEntity<List<User>> getAllUsers() {
		return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
	}
	
	@PostMapping("/utilizador")
	ResponseEntity<User> create(@RequestBody User user) {
	    return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
	}

	@GetMapping("/utilizador/{userId}")
	ResponseEntity<User> getUser(@PathVariable Long userId) {
		User userFound = userService.findUserById(userId);
		if (userFound != null)
			return new ResponseEntity<>(userFound, HttpStatus.OK);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PutMapping("/utilizador")
	ResponseEntity<User> updateUser(@RequestBody User user) {
		User userSaved = userService.findUserById(user.getId());
		if (!userSaved.getUsername().equals(user.getUsername())) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(userService.update(user), HttpStatus.OK);
	}
	
	@DeleteMapping("/utilizador")
	ResponseEntity<User> deleteUser(@RequestBody User user) {
		userService.delete(user);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
