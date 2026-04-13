package com.noman.ems.client.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.noman.ems.client.entity.Client;
import com.noman.ems.client.repository.ClientRepository;
import com.noman.ems.employee.entity.Employee;
import com.noman.ems.employee.repository.EmployeeRepository;
import com.noman.ems.project.entity.Project;
import com.noman.ems.project.repository.ProjectRepository;
import com.noman.ems.util.IdGenerator;

@Service
public class ClientServiceImpl implements ClientService{
	
	@Autowired
	private ClientRepository clientRepo;

	@Autowired
	private ProjectRepository projectRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	// add a new client with auto-generated ID 
	@Override
    public Client add(Client client) {
		//get last client ID fron DB 
		String lastId = clientRepo.findTopByOrderByClientIdDesc()
				.map(Client::getClientId)
				.orElse(null);
		//generate new client id 
		String newId = IdGenerator.generateClientId(lastId);
		client.setClientId(newId);
		
		// save client to DB 
        return clientRepo.save(client);
    }

    @Override
    public List<Client> getAllClients() {
        return clientRepo.findAll();
    }

    @Override
    public Client getClientById(String id) {
        return clientRepo.findById(id)
        		.orElseThrow(()->new RuntimeException("Client not found"));
    }
    
    @Override
    public Client updateClient(Client client) {
    	Optional<Client>existing = clientRepo.findById(client.getClientId());
    	
    	
    	
    	if(existing.isPresent()) {
    		Client old = existing.get();
    		old.setClientName(client.getClientName());
    		old.setContactPersons(client.getContactPersons());
    		old.setRelationshipDate(client.getRelationshipDate());
    		return clientRepo.save(old);
    	}
    	
    	return null; // or throw exception
    	
    }
    
    @Override
    public void deleteClient(String id) {
    	clientRepo.deleteById(id);
    }
	
    @Override
    public List<Project>getProjectsByClientId(String clientId){
    	Client client = clientRepo.findById(clientId)
    					.orElseThrow(()->new RuntimeException(""));
    	
    	if(client.getProjects() ==null) {
    		throw new RuntimeException("No Employees assigned to this project");
    	}
    	
    	return client.getProjects();
    }
    
    @Override
    public String login(String email,String password) {
    	Client client = clientRepo.findByEmail(email)
    			.orElseThrow(()->new RuntimeException("Invalid Email "));
    	
    	//check Lock 
    	if(client.getLockTime()!=null) {
    		if(client.getLockTime().isAfter(LocalDateTime.now())) {
    			throw new RuntimeException("Account locked! Try after 5 menutes ");
    		}
    		else {
    			client.setLockTime(null);
    			client.setFailedAttempts(0);
    		}
    	}
    	
    	//password check 
    	if(passwordEncoder.matches(password, client.getPassword())) {
    		client.setFailedAttempts(0);
    		client.setLockTime(null);
    		clientRepo.save(client);
    		
    		return "Login Succesfull ";
    	}
    	else {
    		int attempts = client.getFailedAttempts()+1;
    		client.setFailedAttempts(attempts);
    		
    		if(attempts >=5) {
    			client.setLockTime(LocalDateTime.now().plusMinutes(5));
    		}
    		
    		clientRepo.save(client);
    		
    		throw new RuntimeException("Invalid password Attempts "+attempts);
    	}
    }
    
	
}
















