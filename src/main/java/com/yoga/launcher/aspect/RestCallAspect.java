package com.yoga.launcher.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class RestCallAspect {
    @Around("@annotation(com.yoga.launcher.annotation.RestCall)")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {

        long startTime = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long executedTime = System.currentTimeMillis() - startTime;
        log.info("Method Signature : {}", joinPoint.getSignature());
        log.info("Request Payload : {}", joinPoint.getArgs()[0]);
        log.info("Response Payload : {}", proceed);
        log.info("Execution Time : {}ms", executedTime);

        return proceed;
    }
}
