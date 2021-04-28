package suncodes.opensource.imports;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 通过@Import注解注册bean
 * 此注解需要在Configuration类上
 */
@Import({TestA.class, ImportConfig2.class, MyImportSelector.class, MyImportBeanDefinitionRegistrar.class})
@Configuration
public class ImportConfig {
}
