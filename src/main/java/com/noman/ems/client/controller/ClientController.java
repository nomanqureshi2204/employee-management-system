package com.noman.ems.client.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.noman.ems.client.entity.Client;
import com.noman.ems.client.service.ClientService;

@RestController
@RequestMapping("clients")
public class ClientController {
	
	@Autowired
	private ClientService service;
	
	//create 
	@PostMapping
	public Client addClient(@RequestBody Client client) {
		return service.add(client);
		}
	
	//read all 
	@GetMapping
	public List<Client>getAllClients(){
		return service.getAllClients();
	}
	
	//read by id 
	@GetMapping("/{id}")
	public Client getClientById(@PathVariable String id) {
		return service.getClientById(id);
	}
	
	//update 
	@PutMapping("/{id}")
	public Client updateClient(@PathVariable String id,@RequestBody Client client) {
		return service.updateClient(id, client);
	}
	
	//delete
	@DeleteMapping("/{id}")
	public String deleteClient(@PathVariable String id) {
		service.deleteClient(id);
		return "Client deleted Successfully with id: "+id;
	}
}











