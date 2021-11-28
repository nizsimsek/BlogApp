package com.nizsimsek.blogapp.dto

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDateTime

@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class PostDto @JvmOverloads constructor (

        val id: String?,
        val title: String,
        val content: String,
        val like: Long,
        val dislike: Long,
        val createdDate: LocalDateTime,

        @JsonInclude(JsonInclude.Include.NON_NULL)
        val updatedDate: LocalDateTime? = null,
        val author: UserDto? = null,
        val tagList: List<TagDto>? = ArrayList(),
        val commentList: List<CommentDto>? = ArrayList()
)
