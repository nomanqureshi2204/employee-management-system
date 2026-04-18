package com.noman.ems.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.noman.ems.entity.ContactPerson;

public interface ContactPersonRepository extends JpaRepository<ContactPerson, Integer>{

}
