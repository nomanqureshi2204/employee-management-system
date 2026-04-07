package com.noman.ems.client.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.noman.ems.client.entity.ContactPerson;
import com.noman.ems.client.repository.ContactPersonRepository;

public class ContactPersonImpl implements ContactPersonService {

	@Autowired
	private ContactPersonRepository repo;

	@Override
	public ContactPerson save(ContactPerson cp) {
		return repo.save(cp);
	}

	@Override
	public List<ContactPerson> getAll() {
		return repo.findAll();
	}

	@Override
	public ContactPerson getById(int id) {
		return repo.findById(id).orElse(null);
	}

	@Override
	public void delete(int id) {
		repo.deleteById(id);
	}
}
