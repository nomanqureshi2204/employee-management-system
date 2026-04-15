package com.noman.ems.client.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.noman.ems.project.entity.Project;

import jakarta.persistence.*;

@Entity
@Table(name = "clients")
public class Client {

    @Id
    private String clientId;

    // ✅ ROLE (must be ROLE_CLIENT)
    private String role;

    private String clientName;

    @Column(unique = true)
    private String email;

    private LocalDate relationshipDate;

    private String password;

    // 🔐 Login fields
    private int failedAttempts;
    private boolean accountLocked;
    private LocalDateTime lockTime;

    // 🔗 One client → many projects
    @JsonIgnore
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Project> projects = new ArrayList<>();

    // 🔗 One client → many contact persons
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ContactPerson> contactPersons = new ArrayList<>();

    // =======================
    // GETTERS & SETTERS
    // =======================

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getRelationshipDate() {
        return relationshipDate;
    }

    public void setRelationshipDate(LocalDate relationshipDate) {
        this.relationshipDate = relationshipDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getFailedAttempts() {
        return failedAttempts;
    }

    public void setFailedAttempts(int failedAttempts) {
        this.failedAttempts = failedAttempts;
    }

    public boolean isAccountLocked() {
        return accountLocked;
    }

    public void setAccountLocked(boolean accountLocked) {
        this.accountLocked = accountLocked;
    }

    public LocalDateTime getLockTime() {
        return lockTime;
    }

    public void setLockTime(LocalDateTime lockTime) {
        this.lockTime = lockTime;
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