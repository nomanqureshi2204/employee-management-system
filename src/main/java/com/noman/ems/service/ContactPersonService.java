package com.noman.ems.service;

import java.util.List;

import com.noman.ems.entity.ContactPerson;

public interface ContactPersonService {
	
	ContactPerson save(ContactPerson cp);

    List<ContactPerson> getAll();

    ContactPerson getById(int id);

    void delete(int id);
}
