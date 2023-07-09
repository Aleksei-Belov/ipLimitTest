package com.project.iplimittest.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class LimitConfiguration {

    @Value("${project.limit.number_of_request}")
    private int numberOfRequests;

    @Value("${project.limit.executing_time}")
    private int executingTime;
}
