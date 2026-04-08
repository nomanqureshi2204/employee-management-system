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
    public Client add(Client client) {
        return repo.save(client);
    }

    @Override
    public List<Client> getAllClients() {
        return repo.findAll();
    }

    @Override
    public Client getClientById(String id) {
        return repo.findById(id)
        		.orElseThrow(()->new RuntimeException("Client not found"));
    }
    
    @Override
    public Client updateClient(String id,Client client) {
    	Client existing = repo.findById(id)
    					  .orElse(null);
    	
    	if(existing != null) {
    		existing.setClientName(client.getClientName());
        	existing.setRelationshipDate(client.getRelationshipDate());
    	}
    	
    	return repo.save(existing);
    	
    }
    
    @Override
    public void deleteClient(String id) {
        repo.deleteById(id);
    }
	
	
}



