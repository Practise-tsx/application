package com.urlapp.app;
//creating spring boot application

import ch.qos.logback.classic.LoggerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableJpaRepositories
public class Application {
    @Autowired
    private Environment env;

    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    @PostConstruct
    public void initApplication() throws IOException {
        LOGGER.info("Running with Spring profile(s) : {}", env.getActiveProfiles());
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @PreDestroy
    public void shutdownLogback(){
        LOGGER.info("Shutting down Logback");
        LoggerContext lCtx = (LoggerContext) LoggerFactory.getILoggerFactory();
        lCtx.stop();
    }

}

