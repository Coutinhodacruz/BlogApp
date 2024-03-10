package com.example.blog.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {


    @Value("${cloud.api.name}")
    private String cloudApiName;

    @Value("${cloud.api.secret}")
    private String cloudApiSecret;

    @Value("${cloud.api.key}")
    private String cloudApiKey;

    public String getCloudApiName() {
        return cloudApiName;
    }


    public String getCloudApiSecret() {
        return cloudApiSecret;
    }


    public String getCloudApiKey() {
        return cloudApiKey;
    }
}
