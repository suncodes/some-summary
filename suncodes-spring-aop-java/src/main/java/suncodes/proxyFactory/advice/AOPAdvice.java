package suncodes.proxyFactory.advice;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.aop.framework.adapter.ThrowsAdviceInterceptor;

import java.lang.reflect.Method;

/**
 * 通知
 */
public class AOPAdvice implements MethodBeforeAdvice, AfterReturningAdvice, MethodInterceptor {


    @Override
    public void afterReturning(Object o, Method method, Object[] objects, Object o1) throws Throwable {
        System.out.println("这是后置通知");
    }

    @Override
    public void before(Method method, Object[] objects, Object o) throws Throwable {
        System.out.println("这是前置通知");
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        return null;
    }
}
