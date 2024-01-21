package com.api.tutorials.configs;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentVariableLogger {

    @Value("${MONGO_UID:admin}")
    private String mongoUid;

    @Value("${MONGO_PWD:password}")
    private String mongoPwd;

    @Value("${MONGO_URI:localhost}")
    private String mongoUri;

    @Value("${MONGO_PORT:27017}")
    private String mongoPort;

    @Value("${MONGO_DB:cars-db}")
    private String mongoDb;

    @PostConstruct
    public void logEnvironmentVariables() {
        System.out.println("MONGO_UID: " + mongoUid);
        System.out.println("MONGO_PWD: " + mongoPwd);
        System.out.println("MONGO_URI: " + mongoUri);
        System.out.println("MONGO_PORT: " + mongoPort);
        System.out.println("MONGO_DB: " + mongoDb);
    }
}
