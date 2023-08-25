package com.example.demomdclogging.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "app.transfer")
public class TransferServiceConfig {
    private String serviceName;
    private int tasksPoolSize;
}
