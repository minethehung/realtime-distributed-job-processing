package com.minethehung.apiserver.service

import com.minethehung.apiserver.dto.JobKafkaDTO
import com.minethehung.apiserver.dto.JobRequestDTO
import com.minethehung.apiserver.entity.Job
import com.minethehung.apiserver.entity.JobStatus
import com.minethehung.apiserver.kafka.JobProducer
import com.minethehung.apiserver.repository.JobRepo
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.UUID


@Service
class JobService (
    private val jobRepo: JobRepo,
    private val jobProducer: JobProducer) {
    @Transactional
    fun createJob(jobRequestDTO: JobRequestDTO): Job {
        val uuid = UUID.randomUUID()
        val payload = jobRequestDTO.payload
        val type = jobRequestDTO.type
        val create = Instant.now()
        val updated = Instant.now()
        val status = JobStatus.CREATED
        val job = Job(uuid, type, payload, 0, status, create, updated)
        return jobRepo.saveAndFlush(job)
    }

    @Transactional
    fun sendJobToKafka(job: Job): Job {
        val jobKafkaDTO = JobKafkaDTO(job.id, job.type, job.payload)
        if (jobProducer.send(jobKafkaDTO)) {
            val updatedJob = Job(job.id, job.type, job.payload, job.retryCount, JobStatus.QUEUED, job.createdAt,
                Instant.now())
            jobRepo.saveAndFlush(updatedJob)
        }
        return job
    }

    fun createAndSendJob(jobRequestDTO: JobRequestDTO): Job {
        return  sendJobToKafka(createJob(jobRequestDTO))
    }
}