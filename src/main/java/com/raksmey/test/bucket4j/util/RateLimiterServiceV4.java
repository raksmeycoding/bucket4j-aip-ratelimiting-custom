//package com.raksmey.test.bucket4j.util;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.raksmey.test.bucket4j.model.ApiKey;
//import com.raksmey.test.bucket4j.repository.ApiKeyRepository;
//import io.github.bucket4j.Bandwidth;
//import io.github.bucket4j.Bucket;
//import io.github.bucket4j.BucketConfiguration;
//import io.github.bucket4j.distributed.proxy.ProxyManager;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//
//import java.time.Duration;
//import java.util.Optional;
//
//@Component
//public class RateLimiterServiceV4 {
//
//    @Autowired
//    private ProxyManager<String> proxyManager;
//
//    @Autowired
//    private RedisTemplate<String, String> redisTemplate;
//
//    @Autowired
//    private ApiKeyRepository apiKeyRepository;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    public Bucket resolveBucket(String apiKey) {
//        String redisKey = "rate-limit:" + apiKey;
//        String bucketStateJson = redisTemplate.opsForValue().get(redisKey);
//        if (bucketStateJson != null) {
//            return proxyManager.builder()
//                    .build(redisKey, () -> deserializeBucketState(bucketStateJson));
//        } else {
//            Optional<ApiKey> dbApiKey = apiKeyRepository.findByApiKey(apiKey);
//            if (dbApiKey.isPresent()) {
//                BucketConfiguration configuration = BucketConfiguration.builder()
//                        .addLimit(Bandwidth.builder().capacity(3).refillGreedy(3, Duration.ofSeconds(8)).build()).build();
//                storeBucketState(redisKey, configuration);
//                return proxyManager.builder()
//                        .build(redisKey, () -> configuration);
//            } else {
//                throw new RuntimeException("API key not found");
//            }
//        }
//    }
//
//    private void storeBucketState(String key, BucketConfiguration configuration) {
//        try {
//            String bucketStateJson = serializeBucketState(configuration);
//            redisTemplate.opsForValue().set(key, bucketStateJson);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private String serializeBucketState(BucketConfiguration configuration) throws JsonProcessingException {
//        return objectMapper.writeValueAsString(configuration);
//    }
//
//    private BucketConfiguration deserializeBucketState(String json) {
//        try {
//            return objectMapper.readValue(json, BucketConfiguration.class);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//            throw new RuntimeException("Failed to deserialize bucket state");
//        }
//    }
//}
