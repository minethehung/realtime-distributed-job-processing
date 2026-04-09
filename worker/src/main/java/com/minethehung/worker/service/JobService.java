package com.minethehung.worker.service;

import com.minethehung.common.JobKafkaDTO;
import com.minethehung.worker.entity.Job;
import com.minethehung.worker.entity.JobStatus;
import com.minethehung.worker.repository.JobRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobService {
    private final JobRepo jobRepo;

    @Transactional
    public void saveJob(JobKafkaDTO jobKafkaDTO) {
        var job = jobRepo.findById(jobKafkaDTO.id()).orElse(null);
        if (job != null) {
            job.setStatus(JobStatus.PROCESSING);
            executeJob(jobRepo.saveAndFlush(job));
        }
    }

    private void executeJob(Job job) {
        System.out.println("Executed job: " + job.getId());
        job.setStatus(JobStatus.SUCCESS);
        jobRepo.saveAndFlush(job);
    }
}
