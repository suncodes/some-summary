package suncodes.spring.aop.autoproxycreator.annotationawareaspectj.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Aspect
public class LogAspect {

    @Pointcut("execution(* suncodes.spring.aop.autoproxycreator.annotationawareaspectj.targets.*.*(..))")
    public void f() {
    }

    @Before(value = "f()")
    public void f1() {
        log.info("AnnotationAwareAspectJAutoProxyCreator LogAspect Before");
    }
}
