package suncodes.aspectj.aopconfig.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LogAspect {

    public void f1() {
        log.info("aop:config LogAspect Before");
    }
}
