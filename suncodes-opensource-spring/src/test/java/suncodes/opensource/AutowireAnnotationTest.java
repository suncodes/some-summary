package suncodes.opensource;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import suncodes.opensource.autowire.annotation.AutowireConfig;
import suncodes.opensource.autowire.annotation.Customer;

public class AutowireAnnotationTest {

    @Test
    public void autowireXmlNo() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AutowireConfig.class);
        Customer customer = context.getBean("customer", Customer.class);
        System.out.println(customer);
    }
}
