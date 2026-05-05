package com.noman.ems.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.noman.ems.entity.Token;
import com.noman.ems.entity.VerificationToken;

public interface TokenRepository extends JpaRepository<VerificationToken, Long>{
	Optional<VerificationToken>findByToken(String token);
}










