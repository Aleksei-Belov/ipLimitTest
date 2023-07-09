package com.project.iplimittest.service.impl;

import com.project.iplimittest.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void testServiceMethod() {
        logger.info("Executing a test method");
    }
}