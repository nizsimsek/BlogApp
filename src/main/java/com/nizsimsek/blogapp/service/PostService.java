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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

        Authentication userDetails = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUsername(userDetails.getName());
        List<Tag> tagList = tagService.findTagListByIdList(request.getTagIds());

        Post post = new Post(
                request.getTitle(),
                request.getContent(),
                user,
                tagList
        );

        return postDtoConverter.convert(postRepository.save(post));
    }

    public List<PostDto> getPostDtoList() {

        Authentication userDetails = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUsername(userDetails.getName());
        List<Post> postList = findPostList();
        postList.forEach(post -> post.setLiked(!postIsLiked(post.getId(), user.getId())));

        return postDtoConverter.convertToPostDtoList(postList);
    }

    public PostDto getPostById(String id) {

        Authentication userDetails = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUsername(userDetails.getName());
        Post post = findPostById(id);
        post.setLiked(!postIsLiked(id, user.getId()));

        return postDtoConverter.convert(post);
    }

    public PostDto updatePost(String id, UpdatePostRequest request) {

        Post post = findPostById(id);
        List<Tag> tagList = tagService.findTagListByIdList(request.getTagIds());

        Post updatedPost = new Post(
                post.getId(),
                request.getTitle(),
                request.getContent(),
                post.getLikes(),
                post.isLiked(),
                post.getCreatedDate(),
                LocalDateTime.now(),
                post.getAuthor(),
                tagList,
                post.getLikedUserList(),
                post.getCommentList()
        );

        return postDtoConverter.convert(postRepository.save(updatedPost));
    }

    public String deletePostById(String id) {

        postRepository.deleteById(id);

        return "Post has been deleted with this id : " + id;
    }

    public PostDto likePostByPostId(String id) {

        Authentication userDetails = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUsername(userDetails.getName());
        Post post = findPostById(id);

        if (postIsLiked(id, user.getId())) {
            post.getLikedUserList().add(user);
            post.setLikes(post.getLikes() + 1);
        } else {
            post.getLikedUserList().remove(user);
            post.setLikes(post.getLikes() - 1);
        }

        postRepository.save(post);

        return getPostById(id);
    }

    protected Boolean postIsLiked(String postId, String userId) {

        Integer trueOrFalse = postRepository.findIsLiked(postId, userId);
        return trueOrFalse.equals(0);
    }

    protected Post findPostById(String id) {

        return postRepository.findById(id)
                .orElseThrow(() -> new GeneralNotFoundException("Post could not found this id : " + id));
    }

    protected List<Post> findPostList() {

        return postRepository.findAll();
    }
}
