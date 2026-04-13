package com.minethehung.worker.kafka;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;

@Configuration
public class KafkaConfig {
    @Bean("fastListenerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, Object> fastListenerFactory(ConsumerFactory<String, Object> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.getContainerProperties().setPollTimeout(2000);
        factory.getContainerProperties().setIdleBetweenPolls(2000);
        return factory;
    }

    @Bean("mediumListenerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, Object> mediumListenerFactory(ConsumerFactory<String, Object> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.getContainerProperties().setPollTimeout(5000);
        factory.getContainerProperties().setIdleBetweenPolls(5000);
        return factory;
    }

    @Bean("slowListenerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, Object> slowListenerFactory(ConsumerFactory<String, Object> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.getContainerProperties().setPollTimeout(15000);
        factory.getContainerProperties().setIdleBetweenPolls(15000);
        return factory;
    }
}