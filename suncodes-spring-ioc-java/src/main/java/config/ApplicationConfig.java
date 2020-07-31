package config;

import bean.IntegerBean;
import org.springframework.context.annotation.*;

/**
 * 声明为java配置文件
 * 使用bean扫描，把注解形式得到bean加载到配置文件中
 */
@Configuration
/**
 * 【1】注解bean ---> java config
 */
@ComponentScan("annotation")
/**
 * 【5】xml config ---> java config
 */
@ImportResource({"application.xml"})
/**
 * 【8】java config ---> java config
 */
@Import(JavaConfig.class)
public class ApplicationConfig {

    /**
     * 使用javaconfig声明bean
     * 【4】xml bean ---> java config
     * @return
     */
    @Bean
    public IntegerBean integerBean() {
        return new IntegerBean();
    }
}
