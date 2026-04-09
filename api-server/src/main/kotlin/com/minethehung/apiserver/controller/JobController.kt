package com.minethehung.apiserver.controller

import com.minethehung.apiserver.dto.JobRequestDTO
import com.minethehung.apiserver.entity.Job
import com.minethehung.apiserver.service.JobService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/jobs")
class JobController (private val jobService: JobService) {
    @PostMapping
    fun createJob(@RequestBody job: JobRequestDTO): ResponseEntity<Job> {
        return ResponseEntity.ok(jobService.createAndSendJob(job))
    }
    @GetMapping
    fun getJobs(): ResponseEntity<List<Job>> {
        return ResponseEntity.ok(jobService.getAllJobs())
    }
}