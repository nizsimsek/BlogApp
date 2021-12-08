package com.nizsimsek.blogapp.dto

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDateTime

@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class PostDto @JvmOverloads constructor (

        val id: String?,
        val title: String,
        val content: String,
        val likes: Long,
        val isLiked: Boolean?,
        val createdDate: LocalDateTime,

        @JsonInclude(JsonInclude.Include.NON_NULL)
        val updatedDate: LocalDateTime? = null,
        val tagList: List<String>? = emptyList(),
        val author: UserDto? = null,
        val likedUserList: List<UserDto>? = emptyList(),
        val commentList: List<CommentDto>? = emptyList()
)
