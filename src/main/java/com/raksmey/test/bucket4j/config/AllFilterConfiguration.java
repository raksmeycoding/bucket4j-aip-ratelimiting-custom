package com.raksmey.test.bucket4j.config;

import com.raksmey.test.bucket4j.filter.RateLimiterTokenBucketFilter;
import com.raksmey.test.bucket4j.util.ResolveBucketService;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class AllFilterConfiguration {
    private final ResolveBucketService resolveBucketService;
    private final RedisTemplate<String, String> redisTemplate;

    public AllFilterConfiguration(ResolveBucketService resolveBucketService, RedisTemplate<String, String> redisTemplate) {
        this.resolveBucketService = resolveBucketService;
        this.redisTemplate = redisTemplate;
    }
//    @Bean
//    public FilterRegistrationBean<RateLimiter> rateLimiterFilter() {
//        FilterRegistrationBean<RateLimiter> registrationBean = new FilterRegistrationBean<>();
//        registrationBean.setFilter(new RateLimiter());
//        registrationBean.addUrlPatterns("/*"); // Apply to all URLs, adjust as necessary
//        registrationBean.setOrder(1); // Set the order of the filter, if needed
//        return registrationBean;
//    }

//    @Bean
//    public FilterRegistrationBean<RateLimiterFilterWithRedis> limiterFilterWithRedisFilterRegistrationBean() {
//        FilterRegistrationBean<RateLimiterFilterWithRedis> registrationBean = new FilterRegistrationBean<>();
//        registrationBean.setFilter(new RateLimiterFilterWithRedis());
//        registrationBean.addUrlPatterns("/*");
//        registrationBean.setOrder(1);
//        return registrationBean;
//    }


//    @Bean
//    FilterRegistrationBean<ApiKeyFilter> apiKeyFilterFilterRegistrationBean() {
//        FilterRegistrationBean<ApiKeyFilter> registrationBean = new FilterRegistrationBean<>();
//        registrationBean.setFilter(new ApiKeyFilter());
//        registrationBean.addUrlPatterns("/*");
//        registrationBean.setOrder(2);
//        return registrationBean;
//    }


    //    @Bean
//    public FilterRegistrationBean<RateLimiterFilterV4> rateLimiterFilter() {
//
//
//
//        FilterRegistrationBean<RateLimiterFilterV4> registrationBean = new FilterRegistrationBean<>();
//
//        registrationBean.setFilter(new RateLimiterFilterV4(rateLimiterServiceV4));
//        registrationBean.addUrlPatterns("*"); // Adjust URL patterns as needed
//
//        return registrationBean;
//    }
//
    @Bean
    public FilterRegistrationBean<RateLimiterTokenBucketFilter> rateLimiterFilter() {


        FilterRegistrationBean<RateLimiterTokenBucketFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new RateLimiterTokenBucketFilter(resolveBucketService, redisTemplate));
        registrationBean.addUrlPatterns("*"); // Adjust URL patterns as needed

        return registrationBean;
    }
}
