package com.minethehung.worker.kafka;

import com.minethehung.common.JobKafkaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobProducer {
    private final KafkaTemplate<String, JobKafkaDTO> kafkaTemplate;

    public void sendJob(JobKafkaDTO job, String topic) {
        kafkaTemplate.send(topic, job.id().toString(), job)
                .whenComplete((result, e) -> {
                    if (e != null) {
                        System.out.println("Can not send job to kafka");
                    } else {
                        System.out.println("Job sent successfully");
                    }
                });
    }

    public void sendJobToDLQ(JobKafkaDTO job) {
        kafkaTemplate.send("job-topic-dlq", job.id().toString(), job)
                .whenComplete((result, e) -> {
                    if (e != null) {
                        System.out.println("Can not send job to kafka");
                    } else {
                        System.out.println("Job sent to DLQ successfully");
                    }
                });
    }
}
