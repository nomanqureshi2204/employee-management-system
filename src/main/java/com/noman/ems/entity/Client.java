package com.noman.ems.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.noman.ems.entity.Project;
import com.noman.ems.entity.User;
import com.noman.ems.service.ContactPersonService;

import jakarta.persistence.*;

@Entity
@Table(name = "clients")
public class Client {

    @Id
    private String clientId;

    private String clientName;

    private LocalDate relationshipDate;

    // 🔥 NEW: User mapping
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    // One client -> many projects
    @JsonIgnore
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Project> projects = new ArrayList<>();

    // One client -> many contact persons
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ContactPerson> contactPersons = new ArrayList<>();

    // ================= GETTERS & SETTERS =================

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public LocalDate getRelationshipDate() {
        return relationshipDate;
    }

    public void setRelationshipDate(LocalDate relationshipDate) {
        this.relationshipDate = relationshipDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public List<ContactPerson> getContactPersons() {
        return contactPersons;
    }

    public void setContactPersons(List<ContactPerson> contactPersons) {
        this.contactPersons = contactPersons;
    }
}