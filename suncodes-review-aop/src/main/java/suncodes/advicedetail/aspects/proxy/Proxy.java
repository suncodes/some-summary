package suncodes.advicedetail.aspects.proxy;

import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Proxy {
    @Bean
    public AnnotationAwareAspectJAutoProxyCreator creator() {
        return new AnnotationAwareAspectJAutoProxyCreator();
    }
}
