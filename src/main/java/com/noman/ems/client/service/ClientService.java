package com.noman.ems.client.service;

import java.util.List;

import com.noman.ems.client.dto.ClientResponseDto;
import com.noman.ems.client.entity.Client;
import com.noman.ems.project.entity.Project;

public interface ClientService {
	 	Client add(Client client);
	    List<Client> getAllClients();
	    Client getClientById(String id);
	    Client updateClient(Client client);
	    void deleteClient(String id);
	    
	    List<Project>getProjectsByClientId(String clientId);
	    String login(String email,String password);
	    
	    List<ClientResponseDto>getAllClientsDto();
	    ClientResponseDto getClientByIdDto(String id);
}









