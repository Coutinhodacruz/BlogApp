package com.example.blog.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.UUID;

@Getter
@Setter
@ToString
@Entity
public class Token {

    @Id
    @GeneratedValue(strategy = UUID)
    private String id;
    private String token;
    private String ownerEmail;
    private LocalDateTime timeCreated = LocalDateTime.now();
}