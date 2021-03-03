package suncodes.spring.aop.autoproxycreator.annotationawareaspectj.proxycreator;

import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 需要把 AnnotationAwareAspectJAutoProxyCreator 配置成bean
 * @author sunchuizhe
 */
@Configuration
public class ProxyCreator {

    @Bean
    public AnnotationAwareAspectJAutoProxyCreator annotationAwareAspectJAutoProxyCreator() {
        return new AnnotationAwareAspectJAutoProxyCreator();
    }
}
