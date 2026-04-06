package com.noman.ems.common.entity;

import lombok.*;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
// Ye class DB me ek table banayegi

@Data 
// getter, setter, toString etc auto generate

@NoArgsConstructor 
@AllArgsConstructor 
@Builder

@Table(name = "tokens") 
// table ka naam

public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // auto increment id
    private Long id;

    @Column(nullable = false)
    private String email; 
    // jis user ke liye token generate hua (employee/client)

    @Column(nullable = false, unique = true)
    private String token; 
    // unique token string (UUID use karenge)

    private LocalDateTime expiryTime; 
    // token kab expire hoga (5 minutes validity)

    private boolean used; 
    // ek baar use hone ke baad true ho jayega (one-time use)
}