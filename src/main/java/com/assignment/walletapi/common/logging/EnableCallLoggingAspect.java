package com.assignment.walletapi.common.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class EnableCallLoggingAspect {
    
    @Around("@annotation(EnableCallLogging)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long initTime = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - initTime;
        System.out.println(
                "============================================================================================================");
        System.out.println("Method Signature is : " + joinPoint.getSignature());
        System.out.println("Method executed in : " + executionTime + "ms");
        System.out.println("Input Request: " + joinPoint.getArgs()[0]); // harus ada arguments (request body, dll)
        System.out.println("Output Response : " + proceed);
        return proceed;
    }
}
