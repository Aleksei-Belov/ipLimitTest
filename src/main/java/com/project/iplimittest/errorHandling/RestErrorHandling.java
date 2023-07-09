package com.project.iplimittest.errorHandling;

import com.project.iplimittest.exception.LimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestErrorHandling {

    @ExceptionHandler(LimitExceededException.class)
    public ResponseEntity<Response> handleException(LimitExceededException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
    }
}
