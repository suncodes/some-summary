package suncodes.advicedetail.advices.advice;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Slf4j
@Component
public class LogAroundAdvice implements MethodInterceptor {

    /**
     * 环绕通知
     * @param methodInvocation 可以在此对象中获取目标类，方法，方法参数，返回值
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        log.info("advice round begin");
        // 被代理方法
        Method method = methodInvocation.getMethod();
        // 被代理方法参数
        Object[] arguments = methodInvocation.getArguments();
        // 被代理类
        Object object = methodInvocation.getThis();
        // 执行方法并返回结果
        Object returnResult = methodInvocation.proceed();
        log.info("advice round end");
        return returnResult;
    }
}
