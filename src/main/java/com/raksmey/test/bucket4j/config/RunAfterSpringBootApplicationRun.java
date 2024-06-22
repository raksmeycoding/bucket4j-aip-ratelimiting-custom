package com.raksmey.test.bucket4j.config;


import com.raksmey.test.bucket4j.model.TokenBucket;
import com.raksmey.test.bucket4j.repository.ApiKeyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;


@Component
public class RunAfterSpringBootApplicationRun implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(RunAfterSpringBootApplicationRun.class);

    @Autowired
    private ApiKeyRepository apiKeyRepository;


    @Override
    public void run(String... args) throws Exception {
        logger.info("CommandLinerRunner runs after spring boot run...");

//        for (int i = 0; i < 10; i++) {
//            ApiKey apiKey = new ApiKey();
//            apiKey.setApiKey(ApiKeyService.generateApiKey());
//            apiKey.setCreatedAt(Instant.now());
//            i = +i;
//            apiKey.setUserId((long) i);
//            apiKeyRepository.save(apiKey);
//        }

//        TokenBucket tokenBucket = new TokenBucket(10, 10, Duration.ofSeconds(3));
//        tokenBucket.tryConsume(1);
//        tokenBucket.tryConsume(1);
//        System.out.println("Available token: " + tokenBucket.getAvailableTokens());
//        Thread.sleep(5000);
//        System.out.println("Available token: " + tokenBucket.getAvailableTokens());


    }
}
