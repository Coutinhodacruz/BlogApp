package com.example.blog.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import static jakarta.persistence.EnumType.STRING;

@Entity
@Table(name = "posts")
@Setter
@Getter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    private String image;

    @Column
    private String category;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
