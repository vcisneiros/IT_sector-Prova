package com.cisneiros.prova;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	    return new ResponseEntity<>(userService.findUserById(userId), HttpStatus.OK);
	}
}
