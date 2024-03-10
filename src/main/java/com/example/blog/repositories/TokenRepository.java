package com.example.blog.repositories;

import com.example.blog.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByOwnerEmail(String email);

}