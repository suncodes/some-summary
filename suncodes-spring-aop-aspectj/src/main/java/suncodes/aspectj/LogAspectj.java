package suncodes.aspectj;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class LogAspectj {

    @Pointcut("execution(* *(..))")
    public void f() {}

    @After("f()")
    public void f1() {
        System.out.println("after");
    }
}
