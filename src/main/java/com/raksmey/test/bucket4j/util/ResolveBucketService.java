package com.raksmey.test.bucket4j.util;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.raksmey.test.bucket4j.model.ApiKey;
import com.raksmey.test.bucket4j.model.TokenBucket;
import com.raksmey.test.bucket4j.repository.ApiKeyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Optional;

@Component
public class ResolveBucketService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private ApiKeyRepository apiKeyRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public TokenBucket resolveBucket(String apiKey) {
        String redisKey = "rate-limit:" + apiKey;
        String bucketStateJson = redisTemplate.opsForValue().get(redisKey);
        if (bucketStateJson != null) {
            return deserializeBucketState(bucketStateJson);
        } else {
            Optional<ApiKey> dbApiKey = apiKeyRepository.findByApiKey(apiKey);
            if (dbApiKey.isPresent()) {
                TokenBucket tokenBucket = new TokenBucket(10, 10, Duration.ofMinutes(1));
                storeBucketState(redisKey, tokenBucket);
                return tokenBucket;
            } else {
                throw new RuntimeException("API key not found");
            }
        }

    }

    private void storeBucketState(String key, TokenBucket tokenBucket) {
        try {
            String bucketStateJson = serializeBucketState(tokenBucket);
            redisTemplate.opsForValue().set(key, bucketStateJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private String serializeBucketState(TokenBucket tokenBucket) throws JsonProcessingException {
        return objectMapper.writeValueAsString(tokenBucket);
    }

    private TokenBucket deserializeBucketState(String json) {
        try {
            return objectMapper.readValue(json, TokenBucket.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to deserialize bucket state");
        }
    }
}
