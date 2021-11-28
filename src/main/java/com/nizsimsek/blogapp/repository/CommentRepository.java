package com.nizsimsek.blogapp.repository;

import com.nizsimsek.blogapp.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, String> {
}
