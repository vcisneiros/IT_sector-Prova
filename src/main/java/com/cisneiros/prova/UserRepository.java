package com.cisneiros.prova;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public interface UserRepository  extends JpaRepository<User, Long> {
	
}
