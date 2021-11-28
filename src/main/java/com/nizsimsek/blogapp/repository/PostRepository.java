package com.nizsimsek.blogapp.repository;

import com.nizsimsek.blogapp.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, String> {
}
