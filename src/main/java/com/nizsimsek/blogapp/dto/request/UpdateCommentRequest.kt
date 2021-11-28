package com.nizsimsek.blogapp.dto.request

import javax.validation.constraints.NotBlank

data class UpdateCommentRequest(

        @field:NotBlank(message = "The content value must not be empty")
        val content: String
)
