package suncodes.spring.aop.proxyfactory.cglib.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

@Slf4j
public class LogAdvice implements MethodBeforeAdvice {
    @Override
    public void before(Method method, Object[] objects, Object o) throws Throwable {
        log.info("前置通知：目标类：{}，方法名：{}，参数：{}",
                o.getClass().getName(), method.getName(), objects);
    }
}
