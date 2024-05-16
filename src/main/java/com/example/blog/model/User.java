package com.example.blog.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Setter
@Getter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String profilePicture;

    private Boolean isAdmin;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;



    @PrePersist
    public void setCreatedAt() {
        this.createdAt = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        createdAt.format(formatter);
    }


    @PreUpdate
    public void setUpdatedAt() {
        this.updatedAt = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        updatedAt.format(formatter);
    }


    public boolean isAdmin() {
        return isAdmin;
    }
}


