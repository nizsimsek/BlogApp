package com.nizsimsek.blogapp.dto.request

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

data class CreatePostRequest(

        @field:NotBlank(message = "The title value must not be empty")
        val title: String,

        @field:NotBlank(message = "The content value must not be empty")
        val content: String,

        @field:NotBlank(message = "The authorId value must not be empty")
        val authorId: String,

        @field:NotEmpty(message = "The tagIds value must not be empty")
        val tagIds: List<String>
)
