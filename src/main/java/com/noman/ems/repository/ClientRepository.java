package com.noman.ems.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.noman.ems.entity.Client;
import com.noman.ems.entity.Employee;

public interface ClientRepository extends JpaRepository<Client, String>{
	Optional<Client> findTopByOrderByClientIdDesc();
	Optional<Client> findByUser_Email(String email);
}
