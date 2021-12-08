package com.nizsimsek.blogapp.dto.converter;

import com.nizsimsek.blogapp.dto.PostDto;
import com.nizsimsek.blogapp.model.Post;
import com.nizsimsek.blogapp.model.Tag;
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
                post.getLikes(),
                post.isLiked(),
                post.getCreatedDate(),
                post.getUpdatedDate(),
                post.getTagList().stream().map(Tag::getName).collect(Collectors.toList())
        );
    }

    public List<PostDto> convertToPostDtoList(List<Post> postList) {

        return postList.stream().map(this::convert).collect(Collectors.toList());
    }
}
