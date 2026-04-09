package com.minethehung.common;

import java.util.Map;
import java.util.UUID;

public record JobKafkaDTO(
        UUID id,
        String type,
        Map<String, Object> payload
) {}