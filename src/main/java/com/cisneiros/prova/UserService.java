package com.cisneiros.prova;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	private UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public List<User> findAll() {
		return userRepository.findAll();	
	}

	public User save(User user) {
		return userRepository.save(user);
	}

	public User findUserById(Long id) {
		return new User();
	}

}
