package com.noman.ems.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.noman.ems.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, String>{
	Optional<Project> findTopByOrderByProjectIdDesc();
	
	
}




