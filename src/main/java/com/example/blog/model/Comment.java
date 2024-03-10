package com.example.blog.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "comments")
@Setter
@Getter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(name = "post_id", nullable = false)
    private String postId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @ElementCollection
    @CollectionTable(name = "comment_likes", joinColumns = @JoinColumn(name = "comment_id"))
    @Column(name = "user_id")
    private List<String> likes;

    @Column(name = "number_of_likes")
    private Integer numberOfLikes;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


}
