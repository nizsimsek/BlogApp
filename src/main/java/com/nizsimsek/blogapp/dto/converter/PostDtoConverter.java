package com.nizsimsek.blogapp.dto.converter;

import com.nizsimsek.blogapp.dto.PostDto;
import com.nizsimsek.blogapp.model.Post;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PostDtoConverter {

    public PostDto convert(Post post) {

        return new PostDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getLike(),
                post.getDislike(),
                post.getCreatedDate(),
                post.getUpdatedDate()
        );
    }

    public List<PostDto> convertToPostDtoList(List<Post> postList) {

        return postList.stream().map(this::convert).collect(Collectors.toList());
    }
}
