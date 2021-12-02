package com.nizsimsek.blogapp.controller;

import com.nizsimsek.blogapp.dto.PostDto;
import com.nizsimsek.blogapp.dto.request.CreatePostRequest;
import com.nizsimsek.blogapp.dto.request.UpdatePostRequest;
import com.nizsimsek.blogapp.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody CreatePostRequest request) {
        return ResponseEntity.ok(postService.createPost(request));
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> getPostList() {
        return ResponseEntity.ok(postService.getPostDtoList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable String id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable String id, @Valid @RequestBody UpdatePostRequest request) {
        return ResponseEntity.ok(postService.updatePost(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable String id) {
        return ResponseEntity.ok(postService.deletePostById(id));
    }

    @PostMapping("/like/{id}")
    public ResponseEntity<PostDto> likePostByPostId(@PathVariable String id) {
        return ResponseEntity.ok(postService.likePostByPostId(id));
    }
}
