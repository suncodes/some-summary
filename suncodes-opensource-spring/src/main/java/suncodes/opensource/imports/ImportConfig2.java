package suncodes.opensource.imports;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ImportConfig2 {

    @Bean
    public TestB testB() {
        return new TestB();
    }
}
