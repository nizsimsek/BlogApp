package com.nizsimsek.blogapp.dto.request

import javax.validation.constraints.NotBlank

data class CreateCommentRequest(

        @field:NotBlank(message = "The content value must not be empty")
        val content: String,

        @field:NotBlank(message = "The postId value must not be empty")
        val postId: String,
)
