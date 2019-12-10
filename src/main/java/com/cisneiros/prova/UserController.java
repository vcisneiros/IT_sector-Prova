package com.cisneiros.prova;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@GetMapping("/utilizadores")
	ResponseEntity<List<User>> getAllUsers() {
		return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
	}
	
	@PostMapping("/utilizadores")
	ResponseEntity<User> create(@RequestBody User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
	    return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
	}

	@GetMapping("/utilizadores/{userId}")
	ResponseEntity<User> getUser(@PathVariable Long userId) {
		User userFound = userService.findUserById(userId);
		if (userFound != null)
			return new ResponseEntity<>(userFound, HttpStatus.OK);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PutMapping("/utilizadores")
	ResponseEntity<User> updateUser(@RequestBody User user) {
		User userSaved = userService.findUserById(user.getId());
		if (!userSaved.getUsername().equals(user.getUsername())) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		user.setCreatedAt(userSaved.getCreatedAt());
		if(!userSaved.getPassword().equals(user.getPassword())) {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
		}
		return new ResponseEntity<>(userService.update(user), HttpStatus.OK);
	}
	
	@DeleteMapping("/utilizadores")
	ResponseEntity<User> deleteUser(@RequestBody User user) {
		userService.delete(user);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
