package com.nizsimsek.blogapp.dto.request

import javax.validation.constraints.NotBlank

data class UpdateTagRequest(

        @field:NotBlank(message = "The name value must not be empty")
        val name: String
)
