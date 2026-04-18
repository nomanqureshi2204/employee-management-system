package com.noman.ems.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.noman.ems.entity.ContactPerson;
import com.noman.ems.repository.ContactPersonRepository;
import com.noman.ems.service.ContactPersonService;

@Service
public class ContactPersonServiceImpl implements ContactPersonService {

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
