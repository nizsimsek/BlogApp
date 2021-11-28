package com.nizsimsek.blogapp.service;

import com.nizsimsek.blogapp.dto.CommentDto;
import com.nizsimsek.blogapp.dto.converter.CommentDtoConverter;
import com.nizsimsek.blogapp.dto.request.CreateCommentRequest;
import com.nizsimsek.blogapp.dto.request.UpdateCommentRequest;
import com.nizsimsek.blogapp.exception.GeneralNotFoundException;
import com.nizsimsek.blogapp.model.Comment;
import com.nizsimsek.blogapp.model.Post;
import com.nizsimsek.blogapp.model.User;
import com.nizsimsek.blogapp.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentDtoConverter commentDtoConverter;
    private final UserService userService;
    private final PostService postService;

    public CommentService(CommentRepository commentRepository,
                          CommentDtoConverter commentDtoConverter,
                          UserService userService,
                          PostService postService) {
        this.commentRepository = commentRepository;
        this.commentDtoConverter = commentDtoConverter;
        this.userService = userService;
        this.postService = postService;
    }

    public CommentDto createComment(CreateCommentRequest request) {

        User user = userService.findUserById(request.getAuthorId());
        Post post = postService.findPostById(request.getPostId());

        Comment comment = new Comment(
                request.getContent(),
                user,
                post
        );

        return commentDtoConverter.convert(commentRepository.save(comment));
    }

    public List<CommentDto> getCommentDtoList() {

        return commentDtoConverter.convertToCommentDtoList(findComments());
    }

    public CommentDto getCommentById(String id) {

        return commentDtoConverter.convert(findCommentById(id));
    }

    public CommentDto updateComment(String id, UpdateCommentRequest request) {

        Comment comment = findCommentById(id);

        Comment updatedComment = new Comment(
                comment.getId(),
                request.getContent(),
                comment.getLike(),
                comment.getDislike(),
                comment.getCreatedDate(),
                comment.getUpdatedDate(),
                comment.getAuthor(),
                comment.getPost()
        );

        return commentDtoConverter.convert(commentRepository.save(updatedComment));
    }

    public String deleteCommentById(String id) {

        Comment comment = findCommentById(id);

        commentRepository.delete(comment);

        return "Comment has been deleted with this id : " + id;
    }

    protected Comment findCommentById(String id) {

        return commentRepository.findById(id).orElseThrow(() -> new GeneralNotFoundException("Comment could not found this id : " + id));
    }

    protected List<Comment> findComments() {

        return commentRepository.findAll();
    }
}
