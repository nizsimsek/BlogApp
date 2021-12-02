package com.nizsimsek.blogapp.dto

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class TagDto @JvmOverloads constructor (

        val id: String?,
        val name: String,
        val postList: List<PostDto>? = emptyList()
)
