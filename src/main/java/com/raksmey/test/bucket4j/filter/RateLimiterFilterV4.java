//package com.raksmey.test.bucket4j.filter;
//
//import com.raksmey.test.bucket4j.util.RateLimiterServiceV4;
//import io.github.bucket4j.Bucket;
//import jakarta.servlet.*;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.apache.coyote.BadRequestException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//
//
//@Component
//public class RateLimiterFilterV4 implements Filter {
//
//
//
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//
//        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
//        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
//
//        String apiKey = httpServletRequest.getHeader("X-Api-Key");
//
//        if (apiKey == null || apiKey.isEmpty() || apiKey.isBlank()) {
//            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "API key is missing");
//            return;
//        }
//
//        try {
//            Bucket bucket = rateLimiterServiceV4.resolveBucket(apiKey);
//            if (bucket.tryConsume(1)) {
//                filterChain.doFilter(httpServletRequest, httpServletResponse);
//            } else {
//                httpServletResponse.sendError(429, "Rate limit exceeded");
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            httpServletResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
//        }
//
//    }
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        Filter.super.init(filterConfig);
//    }
//
//    @Override
//    public void destroy() {
//        Filter.super.destroy();
//    }
//}
