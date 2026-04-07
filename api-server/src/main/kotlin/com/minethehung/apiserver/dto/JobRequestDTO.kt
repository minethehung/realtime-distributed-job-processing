package com.minethehung.apiserver.dto

data class JobRequestDTO(
    val type: String,
    val payload: Map<String, Any>,
)