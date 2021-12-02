package com.nizsimsek.blogapp.dto

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDateTime

@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class CommentDto @JvmOverloads constructor (

        val id: String?,
        val content: String,
        val likes: Long,
        val isLiked: Boolean?,
        val createdDate: LocalDateTime,

        @JsonInclude(JsonInclude.Include.NON_NULL)
        val updatedDate: LocalDateTime? = null,
        val author: UserDto? = null,
        val post: PostDto? = null,
        val likedUserList: List<UserDto>? = emptyList(),
)
