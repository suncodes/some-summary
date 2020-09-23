package service;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class AspectJService {

    @Pointcut("execution(* *.*())")
    public void f() {

    }

    @Before("f()")
    public void f1() {
        System.out.println("AspectJ before");
    }
}
