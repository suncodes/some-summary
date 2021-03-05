package suncodes.advicedetail.advices.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Slf4j
@Component
public class LogBeforeAdvice implements MethodBeforeAdvice {

    /**
     * 前置通知
     * @param method 被被理方法
     * @param objects 方法参数
     * @param o 被代理对象
     * @throws Throwable
     */
    @Override
    public void before(Method method, Object[] objects, Object o) throws Throwable {
        log.info("advice before");
    }
}
