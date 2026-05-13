package com.educloud.config;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfig {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseConfig.class);

    @Value("${spring.datasource.url:NOT_SET}")
    private String dbUrl;

    @Value("${spring.datasource.username:NOT_SET}")
    private String dbUser;

    @PostConstruct
    public void logConnectionInfo() {
        logger.info("Connecting to Database: {}", dbUrl.replaceAll(":.*@", ":****@"));
        logger.info("Database User: {}", dbUser);
    }
}
