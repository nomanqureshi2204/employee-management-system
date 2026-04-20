package com.noman.ems.serviceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.noman.ems.dto.ClientResponseDto;
import com.noman.ems.entity.Client;
import com.noman.ems.entity.Employee;
import com.noman.ems.repository.ClientRepository;
import com.noman.ems.entity.Project;
import com.noman.ems.repository.ProjectRepository;
import com.noman.ems.entity.User;
import com.noman.ems.repository.UserRepository;
import com.noman.ems.service.ClientService;
import com.noman.ems.util.IdGenerator;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepo;

    @Autowired
    private ProjectRepository projectRepo;

    @Autowired
    private UserRepository userRepo;

    // ================= CREATE =================
    @Override
    public Client add(Client client) {

        String lastId = clientRepo.findTopByOrderByClientIdDesc()
                .map(Client::getClientId)
                .orElse(null);

        client.setClientId(IdGenerator.generateClientId(lastId));
        
        User user = client.getUser();
        
        if (user == null) {
			throw new RuntimeException("User is required");
		}

       
        user.setEmail(client.getUser().getEmail());
        user.setRole("ROLE_CLIENT");
        user.setEnabled(true);
        user.setAccountLocked(false);
		user.setFailedAttempts(0);


       

        return clientRepo.save(client);
    }

    // ================= READ =================
    @Override
    public List<Client> getAllClients() {
        return clientRepo.findAll();
    }

    @Override
    public Client getClientById(String id) {
        return clientRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));
    }
    
    
 

    // ================= UPDATE =================
    @Override
    public Client updateClient(String  id,Client client) {

        Client old = clientRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        old.setClientName(client.getClientName());
        old.setRelationshipDate(client.getRelationshipDate());
        old.setContactPersons(client.getContactPersons());

        return clientRepo.save(old);
    }

    // ================= DELETE =================
    @Override
    public void deleteClient(String id) {
        if (!clientRepo.existsById(id)) {
            throw new RuntimeException("Invalid client id");
        }
        clientRepo.deleteById(id);
    }

    // ================= PROJECT =================
    @Override
    public List<Project> getProjectsByClientId(String clientId) {

        Client client = clientRepo.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        return client.getProjects();
    }

    // ================= DTO =================
    private ClientResponseDto convertToDto(Client client) {

        ClientResponseDto dto = new ClientResponseDto();

        dto.setClientId(client.getClientId());
        dto.setClientName(client.getClientName());
        dto.setEmail(client.getUser().getEmail());

        if (client.getProjects() != null) {
            dto.setProjectIds(
                    client.getProjects()
                            .stream()
                            .map(Project::getProjectId)
                            .collect(Collectors.toList())
            );
        }

        return dto;
    }

    @Override
    public List<ClientResponseDto> getAllClientsDto() {
        return clientRepo.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ClientResponseDto getClientByIdDto(String id) {
        return convertToDto(getClientById(id));
    }
}