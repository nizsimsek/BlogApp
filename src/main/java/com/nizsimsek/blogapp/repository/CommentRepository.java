package com.nizsimsek.blogapp.repository;

import com.nizsimsek.blogapp.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, String> {

    @Query(value = "SELECT COUNT(*) FROM comment_likes WHERE (comment_id = ?1 AND user_id = ?2)", nativeQuery = true)
    Integer findIsLiked(String commentId, String userId);
}
