package suncodes.opensource.lifecycle;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@ComponentScan(basePackageClasses = {MyBeanPostProcessor.class})
@Configuration
public class LifeCycleConfig {

//    @Scope("prototype")
    @Bean(initMethod = "init", destroyMethod = "destroy1")
    InitDestroyMethod initDestroyMethod() {
        return new InitDestroyMethod();
    }
}
