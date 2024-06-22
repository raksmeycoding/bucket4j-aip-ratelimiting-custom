//package com.raksmey.test.bucket4j.filter;
//
//
//import io.github.bucket4j.Bucket;
//import io.github.bucket4j.BucketConfiguration;
//import io.github.bucket4j.ConsumptionProbe;
//import io.github.bucket4j.distributed.proxy.ProxyManager;
//import jakarta.servlet.*;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.util.concurrent.TimeUnit;
//import java.util.function.Supplier;
//
//
//@Component
//@Order(2)
//public class RateLimiter4j implements Filter {
//
//    private final Logger logger = LoggerFactory.getLogger(RateLimiter4j.class);
//
//    @Autowired
//    private Supplier<BucketConfiguration> bucketConfiguration;
//
//    @Autowired
//    private ProxyManager<String> proxyManager;
//
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
//        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
//        String key = httpServletRequest.getRemoteAddr();
//        Bucket bucket = proxyManager.builder().build(key, bucketConfiguration);
//
//        ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);
//        logger.debug(">>>>>>>>remainingTokens: {}", probe.getRemainingTokens());
//        if (probe.isConsumed()) {
//            filterChain.doFilter(servletRequest, servletResponse);
//        } else {
//            HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
//            httpResponse.setContentType("text/plain");
//            httpResponse.setHeader("X-Rate-Limit-Retry-After-Seconds", "" + TimeUnit.NANOSECONDS.toSeconds(probe.getNanosToWaitForRefill()));
//            httpResponse.setStatus(429);
//            httpResponse.getWriter().append("Too many requests");
//        }
//    }
//}
