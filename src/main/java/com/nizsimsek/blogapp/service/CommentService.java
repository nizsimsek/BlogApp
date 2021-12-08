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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

        Authentication userDetails = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUsername(userDetails.getName());
        Post post = postService.findPostById(request.getPostId());

        Comment comment = new Comment(
                request.getContent(),
                user,
                post
        );

        return commentDtoConverter.convert(commentRepository.save(comment));
    }

    public List<CommentDto> getCommentDtoList() {

        Authentication userDetails = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUsername(userDetails.getName());
        List<Comment> commentList = findCommentList();
        commentList.forEach(comment -> comment.setLiked(!commentIsLiked(comment.getId(), user.getId())));

        return commentDtoConverter.convertToCommentDtoList(commentList);
    }

    public CommentDto getCommentById(String id) {

        Authentication userDetails = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUsername(userDetails.getName());
        Comment comment = findCommentById(id);
        comment.setLiked(!commentIsLiked(id, user.getId()));

        return commentDtoConverter.convert(comment);
    }

    public CommentDto updateComment(String id, UpdateCommentRequest request) {

        Comment comment = findCommentById(id);

        Comment updatedComment = new Comment(
                comment.getId(),
                request.getContent(),
                comment.getLikes(),
                comment.isLiked(),
                comment.getCreatedDate(),
                comment.getUpdatedDate(),
                comment.getAuthor(),
                comment.getPost(),
                comment.getLikedUserList()
        );

        return commentDtoConverter.convert(commentRepository.save(updatedComment));
    }

    public String deleteCommentById(String id) {

        commentRepository.deleteById(id);

        return "Comment has been deleted with this id : " + id;
    }

    public CommentDto likeCommentByCommentId(String id) {

        Authentication userDetails = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUsername(userDetails.getName());
        Comment comment = findCommentById(id);

        if (commentIsLiked(id, user.getId())) {
            comment.getLikedUserList().add(user);
            comment.setLikes(comment.getLikes() + 1);
        } else {
            comment.getLikedUserList().remove(user);
            comment.setLikes(comment.getLikes() - 1);
        }

        commentRepository.save(comment);
        return getCommentById(id);
    }

    protected Boolean commentIsLiked(String commentId, String userId) {

        Integer trueOrFalse = commentRepository.findIsLiked(commentId, userId);
        return trueOrFalse.equals(0);
    }

    protected Comment findCommentById(String id) {

        return commentRepository.findById(id).orElseThrow(() -> new GeneralNotFoundException("Comment could not found this id : " + id));
    }

    protected List<Comment> findCommentList() {

        return commentRepository.findAll();
    }
}
