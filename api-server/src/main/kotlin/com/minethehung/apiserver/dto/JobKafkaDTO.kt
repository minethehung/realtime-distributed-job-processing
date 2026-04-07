package com.minethehung.apiserver.dto

import java.util.UUID

data class JobKafkaDTO(
    val id: UUID? = null,
    val type: String? = null,
    val payload: Map<String, Any>? = null,
)
