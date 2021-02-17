package com.bbtech.microservices.currencyexchangeservice;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
public class CircuitBreakerController {

    @GetMapping("/sample-api")
    //@Retry(name = "sample-api", fallbackMethod = "defaultResponse") //retried by default 3 times
    //@CircuitBreaker(name = "default", fallbackMethod = "defaultResponse")
    @RateLimiter(name = "default") //in specific it allows only specific no of calls 10s => 1000 calls to an api
    @Bulkhead(name = "sample-api") //at this specific time this many concurrent call can happen
    public String sampleAPi() {
        log.info("Sample API call received!");
        /*ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http://localhost", String.class);
        return forEntity.getBody();*/
        return "sample-api";
    }

    public String defaultResponse(Exception ex) {
        return "fallback response";
    }
}
