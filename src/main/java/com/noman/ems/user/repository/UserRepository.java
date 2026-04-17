package com.noman.ems.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.noman.ems.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	Optional<User>findByEmail(String email);
}
