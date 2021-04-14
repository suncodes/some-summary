package suncodes.spring.aop.autoproxycreator.beanname.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * TODO 注册成一个bean
 * 通过 BeanNameAutoProxyCreator 可以生效
 */
@Component
@Slf4j
public class LogAdvice implements MethodBeforeAdvice {

    /**
     * 前置通知
     * @param method
     * @param objects
     * @param o
     * @throws Throwable
     */
    @Override
    public void before(Method method, Object[] objects, Object o) throws Throwable {
        log.info("BeanNameAutoProxyCreator前置通知：目标类：{}，方法名：{}，参数：{}",
                o.getClass().getName(), method.getName(), objects);
    }
}
