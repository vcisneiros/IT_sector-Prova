package com.cisneiros.prova;

import java.util.List;
import java.util.Optional;

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
		Optional<User> user = userRepository.findById(id);
		return user.isPresent() ? user.get() : null;
	}

	public User update(User user) {
		return userRepository.save(user);
	}

	public void delete(User user) {
		
	}

}
