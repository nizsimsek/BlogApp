package com.nizsimsek.blogapp.service;

import com.nizsimsek.blogapp.dto.PostDto;
import com.nizsimsek.blogapp.dto.converter.PostDtoConverter;
import com.nizsimsek.blogapp.dto.request.CreatePostRequest;
import com.nizsimsek.blogapp.dto.request.UpdatePostRequest;
import com.nizsimsek.blogapp.exception.GeneralNotFoundException;
import com.nizsimsek.blogapp.model.Tag;
import com.nizsimsek.blogapp.model.Post;
import com.nizsimsek.blogapp.model.User;
import com.nizsimsek.blogapp.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostDtoConverter postDtoConverter;
    private final UserService userService;
    private final TagService tagService;

    public PostService(PostRepository postRepository,
                       PostDtoConverter postDtoConverter,
                       UserService userService,
                       TagService tagService) {
        this.postRepository = postRepository;
        this.postDtoConverter = postDtoConverter;
        this.userService = userService;
        this.tagService = tagService;
    }

    public PostDto createPost(CreatePostRequest request) {

        User user = userService.findUserById(request.getAuthorId());
        List<Tag> tagList = tagService.findCategoriesByIdList(request.getTagIds());

        Post post = new Post(
                request.getTitle(),
                request.getContent(),
                user,
                tagList
        );

        return postDtoConverter.convert(postRepository.save(post));
    }

    public List<PostDto> getPostDtoList() {

        return postDtoConverter.convertToPostDtoList(findPosts());
    }

    public PostDto getPostById(String id) {

        return postDtoConverter.convert(findPostById(id));
    }

    public PostDto updatePost(String id, UpdatePostRequest request) {

        Post post = findPostById(id);

        List<Tag> tagList = tagService.findCategoriesByIdList(request.getTagIds());

        Post updatedPost = new Post(
                post.getId(),
                request.getTitle(),
                request.getContent(),
                post.getLike(),
                post.getDislike(),
                post.getCreatedDate(),
                LocalDateTime.now(),
                post.getAuthor(),
                tagList,
                post.getCommentList()
        );

        return postDtoConverter.convert(postRepository.save(updatedPost));
    }

    public String deletePostById(String id) {

        Post post = findPostById(id);

        postRepository.delete(post);

        return "Post has been deleted with this id : " + id;
    }

    protected Post findPostById(String id) {

        return postRepository.findById(id)
                .orElseThrow(() -> new GeneralNotFoundException("Post could not found this id : " + id));
    }

    protected List<Post> findPosts() {

        return postRepository.findAll();
    }
}
