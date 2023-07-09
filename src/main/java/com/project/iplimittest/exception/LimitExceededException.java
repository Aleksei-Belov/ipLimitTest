package com.project.iplimittest.exception;

public class LimitExceededException extends RuntimeException {

    public LimitExceededException(String s) {
        super(s);
    }
}
