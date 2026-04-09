package com.minethehung.worker.repository;

import com.minethehung.worker.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JobRepo extends JpaRepository<Job, UUID> {
}
