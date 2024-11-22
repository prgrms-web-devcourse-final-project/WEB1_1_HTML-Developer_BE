package com.backend.allreva.aop.log;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LogAspect {
    private final LogTracer logTracer;

    @Pointcut("execution(* com.backend.allreva..*Controller.*(..)) || execution(* com.backend.allreva..*Service.*(..)) " +
            "|| execution(* com.backend.allreva..*Repository.*(..)) || execution(* com.backend.allreva..*Scheduler.*(..))")
    public void everyRequest() {}

    @Around("everyRequest()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        TraceStatus traceStatus = null;
        boolean hasException = false;

        try {
            traceStatus = logTracer.begin("Method : " +  getKeySignature(joinPoint),
                    Arrays.deepToString(joinPoint.getArgs()));
            return joinPoint.proceed();
        } catch (Exception ex) {
            logTracer.handleException(traceStatus, ex);
            hasException = true;
            throw ex;
        } finally {
            if(!hasException) logTracer.end(traceStatus);
        }
    }

    private String getKeySignature(ProceedingJoinPoint joinPoint) {
        String[] split = joinPoint.getSignature().toString().split("\\.");
        int length = split.length;
        String[] arr = Arrays.copyOfRange(split, length-3, length);
        return arr[1] + "." + arr[2];
    }
}
