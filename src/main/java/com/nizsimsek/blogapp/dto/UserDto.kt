package com.nizsimsek.blogapp.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class UserDto @JvmOverloads constructor (

        val id: String?,
        val username: String,
        val email: String,
        val firstName: String,
        val lastName: String,

        @JsonIgnore
        val password: String,
        val roleList: List<RoleDto>? = emptyList(),
        val postList: List<PostDto>? = emptyList(),
        val commentList: List<CommentDto>? = emptyList(),
        val likedPostList: List<PostDto>? = emptyList()
)
