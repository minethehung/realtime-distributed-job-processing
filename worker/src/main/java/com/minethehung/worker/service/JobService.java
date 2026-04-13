package com.minethehung.worker.service;

import com.minethehung.common.JobKafkaDTO;
import com.minethehung.worker.entity.Job;
import com.minethehung.worker.entity.JobStatus;
import com.minethehung.worker.kafka.JobProducer;
import com.minethehung.worker.repository.JobRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobService {
    private final JobRepo jobRepo;
    private final RandomService randomService;
    private final JobProducer jobProducer;

    @Transactional
    public void saveJob(JobKafkaDTO jobKafkaDTO) {
        var job = jobRepo.findById(jobKafkaDTO.id()).orElse(null);
        if (job != null && job.getStatus() != JobStatus.SUCCESS && job.getStatus() != JobStatus.PROCESSING) {
            job.setStatus(JobStatus.PROCESSING);
            executeJob(jobRepo.saveAndFlush(job), jobKafkaDTO);
        }
    }

    private void executeJob(Job job, JobKafkaDTO jobKafkaDTO) {
        System.out.println("Executing job " + job.getId().toString() + ", retry: " + job.getRetryCount());
        Boolean status = randomService.genJobStatus();
        if (status) {
            System.out.println("Executed job: " + job.getId() + "\n");
            job.setStatus(JobStatus.SUCCESS);
        }
        else {
            System.out.println("FAIL to Executed job: " + job.getId() + "\n");
            job.setStatus(JobStatus.FAILED);
            if (job.getRetryCount() == 0) {
                job.setRetryCount(job.getRetryCount() + 1);
                jobProducer.sendJob(jobKafkaDTO, "job-topic-retry-2s");
            }
            else if (job.getRetryCount() == 1) {
                job.setRetryCount(job.getRetryCount() + 1);
                jobProducer.sendJob(jobKafkaDTO, "job-topic-retry-5s");
            }
            else if  (job.getRetryCount() == 2) {
                job.setRetryCount(job.getRetryCount() + 1);
                jobProducer.sendJob(jobKafkaDTO, "job-topic-retry-15s");
            }
            else {
                jobProducer.sendJobToDLQ(jobKafkaDTO);
            }
        }
        jobRepo.saveAndFlush(job);
    }
}
