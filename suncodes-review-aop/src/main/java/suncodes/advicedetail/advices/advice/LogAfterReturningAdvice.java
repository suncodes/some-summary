package suncodes.advicedetail.advices.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Slf4j
@Component
public class LogAfterReturningAdvice implements AfterReturningAdvice {

    /**
     * 后置通知
     * @param o 返回值
     * @param method 被代理方法
     * @param objects 方法参数
     * @param o1 目标对象
     * @throws Throwable
     */
    @Override
    public void afterReturning(Object o, Method method, Object[] objects, Object o1) throws Throwable {
        log.info("advice afterReturning");
    }
}
