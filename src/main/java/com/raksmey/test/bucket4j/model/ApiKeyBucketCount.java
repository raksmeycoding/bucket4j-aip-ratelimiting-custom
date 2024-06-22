package com.raksmey.test.bucket4j.model;


import jakarta.persistence.*;

@Entity
@Table(name = "REDIS_API_KEY_BUCKET_COUNT")
public class ApiKeyBucketCount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    String redisKey;

    @Column(nullable = false)
    Long count;

}
