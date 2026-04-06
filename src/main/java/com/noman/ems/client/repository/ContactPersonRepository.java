package com.noman.ems.client.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.noman.ems.client.entity.ContactPerson;

public interface ContactPersonRepository extends JpaRepository<ContactPerson, Integer>{

}
