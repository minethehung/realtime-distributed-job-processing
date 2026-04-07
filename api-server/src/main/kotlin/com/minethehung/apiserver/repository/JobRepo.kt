package com.minethehung.apiserver.repository

import com.minethehung.apiserver.entity.Job
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface JobRepo : JpaRepository<Job, UUID> {
}