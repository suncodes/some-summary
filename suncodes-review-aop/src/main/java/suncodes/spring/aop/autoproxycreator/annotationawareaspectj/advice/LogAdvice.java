package suncodes.spring.aop.autoproxycreator.annotationawareaspectj.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * TODO 注册成一个bean
 * 对于 AnnotationAwareAspectJAutoProxyCreator 来说，只能使用 Advisor ，而不能使用 Advice
 * 可以根据运行结果，看出这个通知没有进行织入
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
        log.info("AnnotationAwareAspectJAutoProxyCreator 前置通知：目标类：{}，方法名：{}，参数：{}",
                o.getClass().getName(), method.getName(), objects);
    }
}
