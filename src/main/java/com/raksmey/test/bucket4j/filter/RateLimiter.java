//package com.raksmey.test.bucket4j.filter;
//
//import io.github.bucket4j.Bandwidth;
//import io.github.bucket4j.Bucket;
//import io.github.bucket4j.Bucket4j;
//import io.github.bucket4j.Refill;
//import jakarta.servlet.Filter;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.FilterConfig;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.ServletRequest;
//import jakarta.servlet.ServletResponse;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.extern.slf4j.Slf4j;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.IOException;
//import java.time.Duration;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//
//public class RateLimiter implements Filter {
//
//    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();
//    public static final Logger logger = LoggerFactory.getLogger(RateLimiter.class);
//
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        Filter.super.init(filterConfig);
//    }
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        logger.info("Rate limiter filter is trigger::{}", servletRequest.getRemoteAddr());
//
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        String ip = request.getRemoteAddr();
//
//        Bucket bucket = buckets.computeIfAbsent(ip, this::createNewBucket);
//
//        if (bucket.tryConsume(1)) {
//            filterChain.doFilter(request, servletResponse);
//        } else {
//            ((HttpServletResponse) servletResponse).setStatus(429);
//        }
//    }
//
//    @Override
//    public void destroy() {
//        Filter.super.destroy();
//    }
//
//    private Bucket createNewBucket(String key) {
//        return Bucket.builder()
//                .addLimit(Bandwidth.classic(3, Refill.greedy(3, Duration.ofMinutes(1))))
//                .build();
//    }
//}
