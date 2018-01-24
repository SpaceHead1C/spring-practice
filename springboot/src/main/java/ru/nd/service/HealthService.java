package ru.nd.service;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HealthService {
    private RestTemplate restTemplate = new RestTemplate();
    private int attempt;

    public void clearAttempt() {
        attempt = 0;
    }

    @Retryable(maxAttempts = 10, value = RuntimeException.class, backoff = @Backoff(delay = 500, multiplier = 2))
    public String getHealth() {
        attempt++;

        return restTemplate.getForObject("http://localhost:1111/health", String.class) + " – " + attempt;
    }

    @Recover
    public String recover() {
        return "fail";
    }
}
