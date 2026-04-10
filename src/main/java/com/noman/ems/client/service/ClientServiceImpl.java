package com.noman.ems.client.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.noman.ems.client.entity.Client;
import com.noman.ems.client.repository.ClientRepository;
import com.noman.ems.util.IdGenerator;

@Service
public class ClientServiceImpl implements ClientService{
	@Autowired ClientRepository repo;
	
	// add a new client with auto-generated ID 
	@Override
    public Client add(Client client) {
		//get last client ID fron DB 
		String lastId = repo.findTopByOrderByClientIdDesc()
				.map(Client::getClientId)
				.orElse(null);
		//generate new client id 
		String newId = IdGenerator.generateClientId(lastId);
		client.setClientId(newId);
		
		// save client to DB 
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
    public Client updateClient(Client client) {
    	Optional<Client>existing = repo.findById(client.getClientId());
    	
    	
    	
    	if(existing.isPresent()) {
    		Client old = existing.get();
    		old.setClientName(client.getClientName());
    		old.setContactPersons(client.getContactPersons());
    		old.setRelationshipDate(client.getRelationshipDate());
    		return repo.save(old);
    	}
    	
    	return null; // or throw exception
    	
    }
    
    @Override
    public void deleteClient(String id) {
        repo.deleteById(id);
    }
	
	
}



