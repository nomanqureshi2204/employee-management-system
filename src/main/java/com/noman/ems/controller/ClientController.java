package com.noman.ems.controller;

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

import com.noman.ems.dto.ClientResponseDto;
import com.noman.ems.entity.Client;
import com.noman.ems.service.ClientService;

import io.swagger.v3.oas.annotations.Operation;

import com.noman.ems.entity.Project;

@RestController
@RequestMapping("clients")
public class ClientController {
	
	@Autowired
	private ClientService service;
	
	//create 
	@PostMapping
	@Operation(summary = "Create Client")
	public Client addClient(@RequestBody Client client) {
		return service.add(client);
		}
	
	//read all 
	@GetMapping
	@Operation(summary = "Get All Clients")
	public List<ClientResponseDto>getAllClients(){
		return service.getAllClientsDto();
	}
	
	//read by id 
	@GetMapping("/{id}")
	@Operation(summary = "Get Client By id")
	public ClientResponseDto getClientById(@PathVariable String id) {
		return service.getClientByIdDto(id);
	}
	
	//update 
	@PutMapping("/{id}")
	@Operation(summary = "Update Client")
	public Client updateClient(@PathVariable String id,@RequestBody Client client) {
		return service.updateClient(id,client);
	}
	
	//delete
	@DeleteMapping("/{id}")
	@Operation(summary = "Delete Client")
	public String deleteClient(@PathVariable String id) {
		service.deleteClient(id);
		return "Client deleted Successfully with id: "+id;
	}
	
	//get project details from the client id 
	@GetMapping("/{clientId}/projects")
	@Operation(summary = "Get project details from the client id")
	public List<Project> getProjectDetailsFromClientId(@PathVariable String clientId) {
		return service.getProjectsByClientId(clientId);
	}
	
}











