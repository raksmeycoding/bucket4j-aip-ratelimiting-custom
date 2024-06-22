//package com.raksmey.test.bucket4j.filter;
//
//
//import io.github.bucket4j.Bandwidth;
//import io.github.bucket4j.Bucket;
//import io.github.bucket4j.Bucket4j;
//import io.github.bucket4j.Refill;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.time.Duration;
//
//public class RateLimiterFilterWithRedis extends OncePerRequestFilter {
//
//
//    @Autowired
//    private RedisTemplate<String, String> redisTemplate;
//    private static final Logger logger = LoggerFactory.getLogger(RateLimiterFilterWithRedis.class);
//
//    @Override
//    protected void doFilterInternal(
//            HttpServletRequest request,
//            HttpServletResponse response,
//            FilterChain filterChain) throws ServletException, IOException {
//
//        logger.info("Inside RateLimiterFilterWithRedis::{}", request.getPathInfo());
//
//        String apiKey = request.getHeader("x-api-key");
//
//        if (apiKey == null || apiKey.isBlank() || apiKey.isEmpty()) {
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            return;
//        }
//
//        // Define rate limit parameters
//        Bandwidth limit = Bandwidth.classic(15, Refill.greedy(15, Duration.ofMinutes(1)));
//
//        // Build bucket locally
//        Bucket bucket = Bucket.builder()
//                .addLimit(limit)
//                .build();
//
//        System.out.println("Bucket::" + bucket.toString());
//
//        // Redis key for storing rate limit info
//        String redisKey = "rate-limit:" + apiKey;
//
//        // Current timestamp in seconds
//        long now = System.currentTimeMillis() / 1000;
//
//        // Get current count from Redis or initialize if it doesn't exist
//        String countStr = redisTemplate.opsForValue().get(redisKey);
//
//
//
//        long count = countStr != null ? Long.parseLong(countStr) : 0;
//
//
//        // Check if request can be consumed
//        if (count < 15) { // 15 is the maximum allowed requests per minute
//            // Increment count and update Redis
//            redisTemplate.opsForValue().set(redisKey, String.valueOf(count + 1), Duration.ofMinutes(1));
//            filterChain.doFilter(request, response); // Allow the request to proceed
//        } else {
//            response.setStatus(429); // Return 429 status for too many requests
//        }
//
//
//
//    }
//}
