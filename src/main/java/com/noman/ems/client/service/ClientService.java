package com.noman.ems.client.service;

import java.util.List;

import com.noman.ems.client.entity.Client;

public interface ClientService {
	 	Client save(Client client);
	    List<Client> getAll();
	    Client getById(String id);
	    void delete(String id);
}
