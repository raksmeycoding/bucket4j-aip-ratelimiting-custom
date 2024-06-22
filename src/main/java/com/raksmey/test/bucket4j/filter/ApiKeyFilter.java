//package com.raksmey.test.bucket4j.filter;
//
//import com.raksmey.test.bucket4j.model.ApiKey;
//import com.raksmey.test.bucket4j.repository.ApiKeyRepository;
//import jakarta.servlet.*;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
////import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.util.Optional;
//
//
//
//
//@Component
//@Order(1)
//public class ApiKeyFilter implements Filter {
//
//    private static final Logger logger = LoggerFactory.getLogger(ApiKeyRepository.class);
//
//    private final ApiKeyRepository apiKeyRepository;
//
//    public ApiKeyFilter(ApiKeyRepository apiKeyRepository) {
//        this.apiKeyRepository = apiKeyRepository;
//    }
//
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
//        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
//        logger.info("Inside ApiKeyFilter::{}", httpServletRequest.getRequestURI());
//
//        String headerApiKey = httpServletRequest.getHeader("x-api-key");
//
//        if (headerApiKey == null || headerApiKey.isEmpty() || headerApiKey.isBlank()) {
//            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            return;
//        }
//
//        Optional<ApiKey> findApiKey = apiKeyRepository.findByApiKey(headerApiKey);
//
//        if (findApiKey.isEmpty() ) {
//            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            return;
//        }
//
//        filterChain.doFilter(servletRequest, servletResponse);
//    }
//}
