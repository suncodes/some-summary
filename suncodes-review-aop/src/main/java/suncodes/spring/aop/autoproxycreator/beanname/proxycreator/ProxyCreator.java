package suncodes.spring.aop.autoproxycreator.beanname.proxycreator;

import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 需要把 BeanNameAutoProxyCreator 配置成bean
 * @author sunchuizhe
 */
@Configuration
public class ProxyCreator {

    @Bean
    public BeanNameAutoProxyCreator beanNameAutoProxyCreator() {
        BeanNameAutoProxyCreator beanNameAutoProxyCreator = new BeanNameAutoProxyCreator();
        beanNameAutoProxyCreator.setBeanNames("userDao");
        beanNameAutoProxyCreator.setInterceptorNames("logAdvice", "logAdvisor");
        return beanNameAutoProxyCreator;
    }
}
