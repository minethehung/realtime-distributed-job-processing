package com.minethehung.worker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RandomService {
    private final Random random;
    public RandomService() {
        random = new Random();
        random.setSeed(System.currentTimeMillis());
    }

    public Boolean genJobStatus() {
        return random.nextBoolean();
    }
}
