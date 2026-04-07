package com.noman.ems.client.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.noman.ems.client.entity.Client;
import com.noman.ems.client.repository.ClientRepository;

@Service
public class ClientServiceImpl implements ClientService{
	@Autowired ClientRepository repo;
	
	@Override
    public Client save(Client client) {
        return repo.save(client);
    }

    @Override
    public List<Client> getAll() {
        return repo.findAll();
    }

    @Override
    public Client getById(String id) {
        return repo.findById(id).orElse(null);
    }
    
    @Override
    public void delete(String id) {
        repo.deleteById(id);
    }
	
	
}