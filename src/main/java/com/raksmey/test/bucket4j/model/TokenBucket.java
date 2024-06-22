package com.raksmey.test.bucket4j.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.netty.channel.socket.DuplexChannel;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;
import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
public class TokenBucket {
    private final long capacity;
    private final long refillTokens;
    private final Duration refillPeriod;
    private long tokens;
    private Instant lastRefillTimestamp;



    // Default constructor needed for Jackson deserialization
    public TokenBucket() {
        this.capacity = 0;
        this.refillTokens = 0;
        this.refillPeriod = Duration.ZERO;
        this.tokens = 0;
        this.lastRefillTimestamp = Instant.now();
    }

    // Constructor with parameters for creating a new TokenBucket
    @JsonCreator
    public TokenBucket(@JsonProperty("capacity") long capacity,
                       @JsonProperty("refillTokens") long refillTokens,
                       @JsonProperty("refillPeriod") Duration refillPeriod) {
        this.capacity = capacity;
        this.refillTokens = refillTokens;
        this.refillPeriod = refillPeriod;
        this.tokens = capacity;
        this.lastRefillTimestamp = Instant.now();
    }


    public synchronized boolean tryConsume(long numTokens) {
        refillTokensIfNeeded();
        if (tokens >= numTokens) {
            tokens -= numTokens;
            return true;
        }

        return false;
    }

    private void refillTokensIfNeeded() {
        Instant now = Instant.now();
        Duration durationSinceLastRefill = Duration.between(lastRefillTimestamp, now);
        if (durationSinceLastRefill.compareTo(refillPeriod) >= 0) {
            long periodsPassed = durationSinceLastRefill.toMillis() / refillPeriod.toMillis();
            long newTokens = periodsPassed * refillTokens;
            tokens = Math.min(capacity, tokens + newTokens);
            lastRefillTimestamp = lastRefillTimestamp.plus(refillPeriod.multipliedBy(periodsPassed));
        }

    }


    public long getAvailableTokens() {
        refillTokensIfNeeded();
        return tokens;
    }

    // Add a method to update Redis state after token consumption
    public void updateRedisState(String redisKey, TokenBucket tokenBucket, RedisTemplate<String, String> redisTemplate) {
        try {
            String bucketStateJson = serializeBucketState(tokenBucket);
            redisTemplate.opsForValue().set(redisKey, bucketStateJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to update Redis state");
        }
    }

    private String serializeBucketState(TokenBucket tokenBucket) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.writeValueAsString(tokenBucket);
    }
}
