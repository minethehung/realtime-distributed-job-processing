package com.minethehung.worker.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "jobs")
public class Job {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "type", length = 50)
    private String type;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "payload")
    private Map<String, Object> payload;

    @Column(name = "status", length = 20)
    @Enumerated(EnumType.STRING)
    private JobStatus status;

    @ColumnDefault("0")
    @Column(name = "retry_count")
    private Integer retryCount;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;


}