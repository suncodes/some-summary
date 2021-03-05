package suncodes.advicedetail.advices.proxy;

import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Proxy {
    @Bean
    public ProxyFactoryBean proxyFactoryBean() {
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setInterceptorNames("logBeforeAdvice",
                "logAfterReturningAdvice", "logAroundAdvice", "logAfterThrowingAdvice");
        proxyFactoryBean.setTargetName("userDao");
        return proxyFactoryBean;
    }
}
