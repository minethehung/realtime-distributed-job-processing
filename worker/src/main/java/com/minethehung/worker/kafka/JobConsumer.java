package com.minethehung.worker.kafka;

import com.minethehung.common.JobKafkaDTO;
import com.minethehung.worker.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobConsumer {
    private final JobService jobService;
    @KafkaListener(
            topics = "job-topic",
            groupId = "job-worker"
    )
    public void consume(JobKafkaDTO message){
        jobService.saveJob(message);
    }

    @KafkaListener(
            topics = "job-topic-retry-2s",
            groupId = "job-worker",
            containerFactory = "fastListenerFactory"
    )
    public void fastRetry(JobKafkaDTO message){
        jobService.saveJob(message);
    }
    @KafkaListener(
            topics = "job-topic-retry-5s",
            groupId = "job-worker",
            containerFactory = "fastListenerFactory"
    )
    public void mediumRetry(JobKafkaDTO message){
        jobService.saveJob(message);
    }

    @KafkaListener(
            topics = "job-topic-retry-15s",
            groupId = "job-worker",
            containerFactory = "fastListenerFactory"
    )
    public void slowRetry(JobKafkaDTO message){
        jobService.saveJob(message);
    }
}
