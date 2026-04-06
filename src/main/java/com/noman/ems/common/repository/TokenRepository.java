package com.noman.ems.common.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.noman.ems.common.entity.Token;

public interface TokenRepository extends JpaRepository<Token, Long>{
	Optional<Token>findByToken(String token);
}










