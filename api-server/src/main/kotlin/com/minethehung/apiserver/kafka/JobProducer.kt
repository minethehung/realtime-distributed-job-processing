package com.minethehung.apiserver.kafka

import com.minethehung.apiserver.dto.JobKafkaDTO
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class JobProducer (
    private val kafkaTemplate: KafkaTemplate<String, JobKafkaDTO>,
) {
    fun send(jobKafkaDTO: JobKafkaDTO): Boolean {
        kafkaTemplate.send("job-topic", jobKafkaDTO.id.toString(), jobKafkaDTO)
            .whenComplete { result, throwable ->
                if (throwable != null) {
                    print("Can not send job to kafka")
                }
                else {
                    println("Job sent successfully $result")
                }
            }
        return true
    }
}