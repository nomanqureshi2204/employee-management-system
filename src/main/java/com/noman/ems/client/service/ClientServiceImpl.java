package com.noman.ems.client.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.noman.ems.client.dto.ClientResponseDto;
import com.noman.ems.client.entity.Client;
import com.noman.ems.client.repository.ClientRepository;
import com.noman.ems.exception.ResourceNotFoundException;
import com.noman.ems.project.entity.Project;
import com.noman.ems.project.repository.ProjectRepository;
import com.noman.ems.util.IdGenerator;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepo;

    @Autowired
    private ProjectRepository projectRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ✅ ADD CLIENT
    @Override
    public Client add(Client client) {

        String lastId = clientRepo.findTopByOrderByClientIdDesc()
                .map(Client::getClientId)
                .orElse(null);

        String newId = IdGenerator.generateClientId(lastId);
        client.setClientId(newId);

        // ✅ ROLE FIX
        client.setRole("ROLE_CLIENT");

        return clientRepo.save(client);
    }

    // ✅ GET ALL
    @Override
    public List<Client> getAllClients() {
        return clientRepo.findAll();
    }

    // ✅ GET BY ID
    @Override
    public Client getClientById(String id) {
        return clientRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));
    }

    // ✅ UPDATE
    @Override
    public Client updateClient(Client client) {

        Client old = clientRepo.findById(client.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));

        old.setClientName(client.getClientName());
        old.setContactPersons(client.getContactPersons());
        old.setRelationshipDate(client.getRelationshipDate());

        return clientRepo.save(old);
    }

    // ✅ DELETE
    @Override
    public void deleteClient(String id) {
        if (!clientRepo.existsById(id)) {
            throw new ResourceNotFoundException("Client not found");
        }
        clientRepo.deleteById(id);
    }

    // ✅ PROJECTS BY CLIENT
    @Override
    public List<Project> getProjectsByClientId(String clientId) {

        Client client = clientRepo.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));

        return client.getProjects();
    }

    // 🔥 LOGIN (FULL FIXED)
    @Override
    public String login(String email, String password) {

        Client client = clientRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid Email"));

        // 🔒 LOCK CHECK
        if (client.getLockTime() != null) {
            if (client.getLockTime().isAfter(LocalDateTime.now())) {
                throw new ResourceNotFoundException("Account locked! Try after 5 minutes");
            } else {
                client.setLockTime(null);
                client.setFailedAttempts(0);
                client.setAccountLocked(false);
            }
        }

        // 🔑 PASSWORD CHECK
        if (passwordEncoder.matches(password, client.getPassword())) {

            client.setFailedAttempts(0);
            client.setLockTime(null);
            client.setAccountLocked(false);
            clientRepo.save(client);

            // ✅ ROLE FIX
            String role = client.getRole();
            if (!role.startsWith("ROLE_")) {
                role = "ROLE_" + role;
            }

            // 🔥 AUTH SET
            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(
                            client.getEmail(),
                            null,
                            Collections.singleton(new SimpleGrantedAuthority(role))
                    );

            SecurityContextHolder.getContext().setAuthentication(auth);

            return "Client Login Successful";

        } else {

            int attempts = client.getFailedAttempts() + 1;
            client.setFailedAttempts(attempts);

            if (attempts >= 5) {
                client.setLockTime(LocalDateTime.now().plusMinutes(5));
                client.setAccountLocked(true);
            }

            clientRepo.save(client);

            throw new ResourceNotFoundException("Invalid password. Attempts: " + attempts);
        }
    }

    // ✅ DTO CONVERTER
    private ClientResponseDto convertToDto(Client client) {

        ClientResponseDto dto = new ClientResponseDto();

        dto.setClientId(client.getClientId());
        dto.setClientName(client.getClientName());
        dto.setEmail(client.getEmail());
        dto.setRelationshipDate(
                client.getRelationshipDate() != null
                        ? client.getRelationshipDate().toString()
                        : null
        );

        if (client.getProjects() != null) {
            dto.setProjectIds(client.getProjects()
                    .stream()
                    .map(Project::getProjectId)
                    .toList());
        }

        if (client.getContactPersons() != null) {
            dto.setContactPersons(client.getContactPersons()
                    .stream()
                    .map(c -> c.getName())
                    .toList());
        }

        return dto;
    }

    // ✅ DTO METHODS
    @Override
    public List<ClientResponseDto> getAllClientsDto() {
        return clientRepo.findAll()
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public ClientResponseDto getClientByIdDto(String id) {
        return convertToDto(getClientById(id));
    }
}