package suncodes.aspectj;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.SourceLocation;
import suncodes.pojo.bo.HelloBO;
import suncodes.service.TargetService;

@Aspect
public class LogAspectj {

    @Pointcut("execution(* *(..))")
    public void f() {}

    @Pointcut("execution(void *(..))")
    public void ff() {}

//    /**
//     * 主要学习JoinPoint的用法
//     * @param joinPoint
//     */
//    @Before("ff()")
//    public void f1(JoinPoint joinPoint) {
//        // 获取方法签名
//        Signature signature = joinPoint.getSignature();
//        // void suncodes.service.TargetService.f(String)
//        System.out.println(signature);
//        // 获取参数值，而不是参数名
//        Object[] args = joinPoint.getArgs();
//        System.out.println(args.length);
//        for (Object arg : args) {
//            System.out.println(arg);
//        }
//        // toString方法，切点表达式execution(void suncodes.service.TargetService.f(String))
//        System.out.println(joinPoint.toString());
//        // 返回连接点的类型method-execution
//        System.out.println(joinPoint.getKind());
//        // 返回连接点方法所在类文件中的位置
//        // org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint$SourceLocationImpl@3bd82cf5
//        System.out.println(joinPoint.getSourceLocation());
//        SourceLocation sourceLocation = joinPoint.getSourceLocation();
//        // 报错 java.lang.UnsupportedOperationException
////        System.out.println(sourceLocation.getFileName());
////        System.out.println(sourceLocation.getLine());
//        // 被代理的类，即目标类
//        System.out.println(sourceLocation.getWithinType().getName());
//        // 访问连接点的静态部分，如被通知方法签名、连接点类型等
//        System.out.println(joinPoint.getStaticPart());
//        // 目标（指的是代理类依据目标来生成的）
//        Object target = joinPoint.getTarget();
//        System.out.println(target);
//        // 代理（指的是动态生成的类）
//        System.out.println(joinPoint.getThis());
//    }



    /*@Before("within(suncodes.*)")
    public void f2() {
        // 匹配suncodes包下的类，不包括子包
        System.out.println("这是前置通知f2");
    }

    @Before("within(suncodes..*)")
    public void f3() {
        // 匹配suncodes包及子包下的类
        System.out.println("这是前置通知f3");
    }

    @Before("this(targetService)")
    public void f4(TargetService targetService) {
        // 代理类
        System.out.println("这是前置通知f4");
    }

    @Before("this(suncodes.service.TargetService)")
    public void f5() {
        // 代理类，参数必须是类名
        System.out.println("这是前置通知f5");
    }

    @Before("args(String)")
    public void f6() {
        // 参数为String类型
        System.out.println("这是前置通知f6");
    }

    @Before("args(a)")
    public void f7(String a) {
        // 参数为String类型
        System.out.println("这是前置通知f7");
        // 这种方式同时可以获取对应的参数值
        System.out.println(a);
    }

    @Before("@within(Deprecated)")
    public void f8() {
        // 标注的需要是类
        System.out.println("这是前置通知f8");
    }

    @Before("@within(Deprecated) && @annotation(Deprecated)")
    public void f9() {
        // 标注的需要是方法
        System.out.println("这是前置通知f9");
    }

    @Before("@within(Deprecated) && @annotation(deprecated)")
    public void f10(Deprecated deprecated) {
        // 标注的需要是方法，通过这种方式可以获取对应注解的值
        System.out.println("这是前置通知f10");
    }

    @Before("bean(targetService)")
    public void f11() {
        // 参数就是spring bean的名称，且在AOP中的级别比较高
        System.out.println("这是前置通知f11");
    }

    @Before(value = "@within(Deprecated) && args(a)", argNames = "a")
    public void f12(String a) {
        // argNames的用法
        // 1、argNames出现，则args()函数必须出现，成对的
        // 2、f12方法中的参数和argNames和args()中的参数，参数名必须一致。
        // 3、有了argNames，则f12中的参数顺序已经不重要了，可以调换
        // 4、argNames(用在注解中)与arg-names(用在XML中),他们是同一个东西.
        System.out.println("这是前置通知f12");
    }*/


//    /**
//     * 1、后置通知
//     * 2、returning：目标方法的返回值
//     * 3、args()：如果不加argNames，则按照参数的顺序，参数名则可以随意写。
//     * @param a
//     * @param b
//     * @param c
//     */
//    @AfterReturning(value = "f() && args(b, c)", returning = "a", argNames = "a,c,b")
//    public void afterReturning1(String a, String c, String b) {
//        System.out.println("参数值：" + b);
//        System.out.println("参数值：" + c);
//        System.out.println("返回值:" + a);
//        System.out.println("后置通知a1");
//    }

//    /**
//     * 1、异常通知
//     * 通知中的参数可以是其父类，其他类或子类匹配不了
//     * @param e
//     */
//    @AfterThrowing(value = "f()", throwing = "e")
//    public void afterThrowing1(RuntimeException e) {
////        e.printStackTrace();
//        System.out.println("异常通知：afterThrowing1");
//    }

    @Around("f()")
    public Object around(ProceedingJoinPoint joinPoint) {
        Object resutObject = null;
        try {
            //获取参数列表
            Object[] args = joinPoint.getArgs();
            resutObject = joinPoint.proceed(args);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return resutObject;
    }
}
