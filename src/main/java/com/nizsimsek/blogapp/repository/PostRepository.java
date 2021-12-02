package com.nizsimsek.blogapp.repository;

import com.nizsimsek.blogapp.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, String> {

    @Query(value = "SELECT COUNT(*) FROM post_likes WHERE (post_id = ?1 AND user_id = ?2)", nativeQuery = true)
    Integer findIsLiked(String postId, String userId);
}
