package com.project.iplimittest.controller;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/limit")
@AllArgsConstructor
public class LimitRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping()
    public ResponseEntity<HttpStatus> getApi() {
        logger.info("Executing the main get API");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
