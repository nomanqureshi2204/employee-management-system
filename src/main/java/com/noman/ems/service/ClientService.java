package com.noman.ems.service;

import java.util.List;

import com.noman.ems.dto.ClientResponseDto;
import com.noman.ems.entity.Client;
import com.noman.ems.entity.Project;

public interface ClientService {
	 	Client add(Client client);
	    List<Client> getAllClients();
	    Client getClientById(String id);
	    Client updateClient(Client client);
	    void deleteClient(String id);
	    
	    List<Project>getProjectsByClientId(String clientId);
	
	    
	    List<ClientResponseDto>getAllClientsDto();
	    ClientResponseDto getClientByIdDto(String id);
}









