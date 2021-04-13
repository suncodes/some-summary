package suncodes.opensource.autowire.annotation.autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@ComponentScan(basePackages = {"suncodes.opensource.autowire.annotation.autowired"})
@Configuration
public class AutowireConfig {

    @Bean
    Address address() {
        Address address = new Address();
        address.setFulladdress("另外一个address");
        return address;
    }

    @Bean
    Address addressByName() {
        Address address = new Address();
        address.setFulladdress("addressByName");
        return address;
    }

    @Primary
    @Bean
    Address addressByPrimary() {
        Address address = new Address();
        address.setFulladdress("addressByPrimary");
        return address;
    }
}
