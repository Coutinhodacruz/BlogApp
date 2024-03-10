package com.example.blog.repositories;

import com.example.blog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepostory extends JpaRepository<Post, Long> {

}
