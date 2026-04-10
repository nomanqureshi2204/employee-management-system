package com.noman.ems.client.service;

import java.util.List;

import com.noman.ems.client.entity.Client;

public interface ClientService {
	 	Client add(Client client);
	    List<Client> getAllClients();
	    Client getClientById(String id);
	    Client updateClient(Client client);
	    void deleteClient(String id);
}









