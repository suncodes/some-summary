package suncodes.spring.aop.autoproxycreator.defaultadvisor.advisor;

        import lombok.extern.slf4j.Slf4j;
        import org.springframework.aop.MethodBeforeAdvice;
        import org.springframework.aop.aspectj.AspectJExpressionPointcut;
        import org.springframework.aop.support.DefaultPointcutAdvisor;
        import org.springframework.stereotype.Component;

        import java.lang.reflect.Method;

/**
 * 自定义一个Advisor
 * @author sunchuizhe
 */
@Component
@Slf4j
public class LogAdvisor extends DefaultPointcutAdvisor {
    public LogAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* suncodes.spring.aop.autoproxycreator.defaultadvisor.targets.*.*(..))");
        super.setPointcut(pointcut);
        super.setAdvice(new MethodBeforeAdvice() {
            @Override
            public void before(Method method, Object[] objects, Object o) throws Throwable {
                log.info("DefaultAdvisorAutoProxyCreator LogAdvisor MethodBeforeAdvice");
            }
        });
    }
}
