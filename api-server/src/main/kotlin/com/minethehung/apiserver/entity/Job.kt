package com.minethehung.apiserver.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.time.Instant
import java.util.UUID

@Entity
@Table(name = "jobs")
open class Job {
    constructor(
        id: UUID?,
        type: String?,
        payload: Map<String, Any>?,
        retryCount: Int?,
        status: JobStatus?,
        createdAt: Instant?,
        updatedAt: Instant?
    ) {
        this.id = id
        this.type = type
        this.payload = payload
        this.retryCount = retryCount
        this.status = status
        this.createdAt = createdAt
        this.updatedAt = updatedAt
    }

    @Id
    @Column(name = "id", nullable = false)
    open var id: UUID? = null

    @Column(name = "type", length = 50)
    open var type: String? = null

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "payload")
    open var payload: Map<String, Any>? = null

    @Column(name = "status", length = 20)
    @Enumerated(EnumType.STRING)
    open var status: JobStatus? = null

    @ColumnDefault("0")
    @Column(name = "retry_count")
    open var retryCount: Int? = null

    @Column(name = "created_at")
    open var createdAt: Instant? = null

    @Column(name = "updated_at")
    open var updatedAt: Instant? = null

}