package suncodes.opensource.ext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(basePackages = "suncodes.opensource.ext")
@Configuration
public class ExtConfig {

    @Bean
    public MyApplicationEvent myApplicationEvent() {
        return new MyApplicationEvent("<<<<");
    }
}
