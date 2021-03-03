package suncodes.spring.aop.autoproxycreator.defaultadvisor.proxycreator;

import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 需要把 DefaultAdvisorAutoProxyCreator 配置成bean
 * @author sunchuizhe
 */
@Configuration
public class ProxyCreator {

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        return new DefaultAdvisorAutoProxyCreator();
    }
}
