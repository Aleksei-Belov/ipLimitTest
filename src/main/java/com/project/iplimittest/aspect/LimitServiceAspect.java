package com.project.iplimittest.aspect;

import com.project.iplimittest.service.LimitRequestService;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LimitServiceAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LimitRequestService limitRequestService;

    @Pointcut("execution(public * com.project.iplimittest.controller.LimitRestController.*(..))")
    public void callRestApi(){}

    //pointcut to test executing service methods
    @Pointcut("execution(public * com.project.iplimittest.service.TestService.*(..))")
    public void callServiceMethod(){}

    @Before("callRestApi()")
    public void beforeCallRestApi(){
        logger.info("Aspect beforeRestApi");
        limitRequestService.checkLimitForRequests();
    }

    @Before("callServiceMethod()")
    public void beforeCallServiceMethod(){
        logger.info("Aspect beforeServiceMethod");
        limitRequestService.checkLimitForRequests();
    }
}
