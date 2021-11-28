package com.nizsimsek.blogapp.dto.converter;

import com.nizsimsek.blogapp.dto.CommentDto;
import com.nizsimsek.blogapp.model.Comment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommentDtoConverter {

    public CommentDto convert(Comment comment) {

        return new CommentDto(
                comment.getId(),
                comment.getContent(),
                comment.getLike(),
                comment.getDislike(),
                comment.getCreatedDate(),
                comment.getUpdatedDate()
        );
    }

    public List<CommentDto> convertToCommentDtoList(List<Comment> commentList) {

        return commentList.stream().map(this::convert).collect(Collectors.toList());
    }
}
