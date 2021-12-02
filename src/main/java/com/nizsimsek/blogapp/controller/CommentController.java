package com.nizsimsek.blogapp.controller;

import com.nizsimsek.blogapp.dto.CommentDto;
import com.nizsimsek.blogapp.dto.PostDto;
import com.nizsimsek.blogapp.dto.request.CreateCommentRequest;
import com.nizsimsek.blogapp.dto.request.UpdateCommentRequest;
import com.nizsimsek.blogapp.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {
    
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<CommentDto> createComment(@Valid @RequestBody CreateCommentRequest request) {
        return ResponseEntity.ok(commentService.createComment(request));
    }

    @GetMapping
    public ResponseEntity<List<CommentDto>> getCommentList() {
        return ResponseEntity.ok(commentService.getCommentDtoList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable String id) {
        return ResponseEntity.ok(commentService.getCommentById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable String id, @Valid @RequestBody UpdateCommentRequest request) {
        return ResponseEntity.ok(commentService.updateComment(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCommentById(@PathVariable String id) {
        return ResponseEntity.ok(commentService.deleteCommentById(id));
    }

    @PostMapping("/like/{id}")
    public ResponseEntity<CommentDto> likeCommentByCommentId(@PathVariable String id) {
        return ResponseEntity.ok(commentService.likeCommentByCommentId(id));
    }
}
