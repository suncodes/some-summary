package suncodes.aspectj.annotation.proxycreator;

import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 需要把 AnnotationAspectJ 配置成bean
 * @EnableAspectJAutoProxy 可以自动扫描@Aspect注解以及Advisor队列
 * @author sunchuizhe
 */
@Configuration
@EnableAspectJAutoProxy
public class ProxyCreator {
}
