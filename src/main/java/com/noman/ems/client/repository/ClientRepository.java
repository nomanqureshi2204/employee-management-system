package com.noman.ems.client.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.noman.ems.client.entity.Client;

public interface ClientRepository extends JpaRepository<Client, String>{
	
}
