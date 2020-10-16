package suncodes.aop.java.config;

import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import suncodes.aop.java.aspect.AspectService;

@Configuration
public class AOPConfig {

    @Autowired
    private AspectService aspectService;
    @Bean
    public AspectJExpressionPointcutAdvisor aspectJExpressionPointcutAdvisor() {
        AspectJExpressionPointcutAdvisor aspectJExpressionPointcutAdvisor =
                new AspectJExpressionPointcutAdvisor();
        aspectJExpressionPointcutAdvisor.setExpression("execution(* suncodes.aop.java.service..*(..))");
        aspectJExpressionPointcutAdvisor.setAdvice(aspectService);
        return aspectJExpressionPointcutAdvisor;
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator =
                new DefaultAdvisorAutoProxyCreator();
        // spring aop代理混用的问题，所以需要设置为true
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }
}
