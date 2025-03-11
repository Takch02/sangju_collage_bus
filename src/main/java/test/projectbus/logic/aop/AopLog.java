package test.projectbus.logic.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class AopLog {

    @Before(value = "execution(*  test.projectbus.logic..*.*(..))")
    public void logTest(JoinPoint joinPoint) {

        String shortString = joinPoint.getSignature().toShortString();
        log.info("method : [{}]", shortString);
    }
}
