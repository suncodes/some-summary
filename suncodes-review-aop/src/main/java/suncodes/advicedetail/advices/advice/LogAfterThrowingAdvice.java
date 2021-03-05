package suncodes.advicedetail.advices.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.stereotype.Component;

/**
 * 看接口说明可知，该接口上没有任何方法，但是实现了这个接口的类必须至少实现以下4个方法中的一个，否则程序报：
 * Caused by: java.lang.IllegalArgumentException: At least one handler method must be found in class
 * 接口中的异常类可以为自己自定义的异常类，方法是通过反射调用。
 * public void afterThrowing(Exception ex)
 * public void afterThrowing(RemoteException e)
 * public void afterThrowing(Method method, Object[] args, Object target, Exception ex)
 * public void afterThrowing(Method method, Object[] args, Object target, ServletException ex)
 * @author sunchuizhe
 */
@Slf4j
@Component
public class LogAfterThrowingAdvice implements ThrowsAdvice {

    public void afterThrowing(Exception ex) {
        log.info("advice afterThrowing");
    }
}
