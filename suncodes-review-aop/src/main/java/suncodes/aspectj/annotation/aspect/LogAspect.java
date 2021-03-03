package suncodes.aspectj.annotation.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Aspect
public class LogAspect {

    @Pointcut("execution(* suncodes.aspectj.annotation.targets.*.*(..))")
    public void f() {
    }

    @Before(value = "f()")
    public void f1() {
        log.info("AnnotationAspectJ LogAspect Before");
    }
}
