package suncodes.opensource;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import suncodes.opensource.autowire.xml.Customer;

public class AutowireTest {
    @Test
    public void autowireXmlNo() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("autowire/AutowireXmlNo.xml");
        Customer customer = context.getBean("customer", Customer.class);
        System.out.println(customer);
    }

    @Test
    public void autowireXmlByName() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("autowire/AutowireXmlByName.xml");
        Customer customer = context.getBean("customer", Customer.class);
        System.out.println(customer);
    }

    @Test
    public void autowireXmlByType() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("autowire/AutowireXmlByType.xml");
        Customer customer = context.getBean("customer", Customer.class);
        System.out.println(customer);
    }

    @Test
    public void autowireXmlConstructor() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("autowire/AutowireXmlConstructor.xml");
        Customer customer = context.getBean("customer", Customer.class);
        System.out.println(customer);
    }
}
