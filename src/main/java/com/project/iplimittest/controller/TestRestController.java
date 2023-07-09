package com.project.iplimittest.controller;

import com.project.iplimittest.service.TestService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@AllArgsConstructor
public class TestRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TestService testService;

    @GetMapping("/callTestMethod")
    public ResponseEntity<HttpStatus> callTestMethod() {
        logger.info("Executing a test get API");
        testService.testServiceMethod();

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
