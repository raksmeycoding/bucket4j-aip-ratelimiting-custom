package com.raksmey.test.bucket4j.filter;

import com.raksmey.test.bucket4j.model.TokenBucket;
import com.raksmey.test.bucket4j.util.ResolveBucketService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.IOException;

@Order(2)
public class RateLimiterTokenBucketFilter implements Filter {

    private final ResolveBucketService resolveBucket;


    private final RedisTemplate<String, String> redisTemplate;

    public RateLimiterTokenBucketFilter(ResolveBucketService resolveBucket, RedisTemplate<String, String> redisTemplate) {
        this.resolveBucket = resolveBucket;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        String apiKey = httpServletRequest.getHeader("X-Api-Key");

        if (apiKey == null || apiKey.isEmpty() || apiKey.isBlank()) {
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "API key is missing");
            return;
        }

        try {
            TokenBucket tokenBucket = resolveBucket.resolveBucket(apiKey);
            if (tokenBucket.tryConsume(1)) {
                System.out.println("My token bucket");
                tokenBucket.updateRedisState("rate-limit:" + apiKey, tokenBucket, redisTemplate);
                filterChain.doFilter(httpServletRequest, httpServletResponse);
            } else {
                httpServletResponse.sendError(429, "Rate limit exceeded");
            }

        } catch (Exception e) {
            e.printStackTrace();
            httpServletResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
