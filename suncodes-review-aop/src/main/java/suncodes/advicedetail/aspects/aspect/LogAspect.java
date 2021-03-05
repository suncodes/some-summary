package suncodes.advicedetail.aspects.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LogAspect {

    @Pointcut("execution(* suncodes.advicedetail.aspects.targets.*.*(..))")
    public void f() {
    }

    /**
     * public interface JoinPoint {
     *     String toString();         //连接点所在位置的相关信息
     *     String toShortString();     //连接点所在位置的简短相关信息
     *     String toLongString();     //连接点所在位置的全部相关信息
     *     Object getThis();         //返回AOP代理对象
     *     Object getTarget();       //返回目标对象
     *     Object[] getArgs();       //返回被通知方法参数列表
     *     Signature getSignature();  //返回当前连接点签名
     *     SourceLocation getSourceLocation();//返回连接点方法所在类文件中的位置
     *     String getKind();        //连接点类型
     *     StaticPart getStaticPart(); //返回连接点静态部分
     * }
     * @param joinPoint
     */
    @Before("f()")
    public void f1(JoinPoint joinPoint) {
        log.info("前置通知 ---- begin ----");
        printJoinPoint(joinPoint);
        log.info("前置通知 ---- end ----");
    }

    private void printJoinPoint(JoinPoint joinPoint) {
        // 获取被代理类
        Object target = joinPoint.getTarget();
        System.out.println(target.getClass().getName());
        // 方法签名
        Signature signature = joinPoint.getSignature();
        // 实现的接口类
        System.out.println(signature.getDeclaringType().getName());

        // 代理类
        Object aThis = joinPoint.getThis();
        System.out.println(aThis.getClass().getName());
    }

    /**
     * 后置通知
     * @param joinPoint
     * @param result
     */
    @AfterReturning(pointcut = "f()", returning = "result")
    public void f2(JoinPoint joinPoint, Object result) {
        log.info("后置通知 ---- begin ----");
        printJoinPoint(joinPoint);

        // 打印返回值
        System.out.println(result);

        log.info("后置通知 ---- end ----");
    }

    /**
     * 环绕通知
     * @param joinPoint 使用 ProceedingJoinPoint
     */
    @Around("f()")
    public Object f3(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("环绕通知 ---- begin ----");
        printJoinPoint(joinPoint);
        // 执行目标方法
        Object o = joinPoint.proceed();
        System.out.println(o);
        log.info("环绕通知 ---- end ----");
        return o;
    }

    /**
     * 异常通知
     * @param joinPoint
     * @param ex
     */
    @AfterThrowing(value = "f()", throwing = "ex")
    public void f5(JoinPoint joinPoint, Exception ex) {
        log.info("异常通知 ---- begin ----");
        printJoinPoint(joinPoint);
        log.info("", ex);
        log.info("异常通知 ---- end ----");
    }

    @After(value = "f()")
    public void f6(JoinPoint joinPoint) {
        log.info("最终通知 ---- begin ----");
        printJoinPoint(joinPoint);
        log.info("最终通知 ---- end ----");
    }
}
