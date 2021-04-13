package suncodes.opensource;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import suncodes.opensource.autowire.annotation.autowired.AutowireConfig;
import suncodes.opensource.autowire.annotation.autowired.CustomerByName;
import suncodes.opensource.autowire.annotation.autowired.CustomerByPrimary;
import suncodes.opensource.autowire.annotation.autowired.CustomerDefault;
import suncodes.opensource.autowire.annotation.resource.ResourceConfig;

public class AutowireAnnotationTest {

    /**
     * autowired 注解默认使用 type 注入
     * 当 同一个 type 有多个的时候，安装 name 注入
     */
    @Test
    public void autowireAutowiredDefault() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AutowireConfig.class);
        CustomerDefault customer = context.getBean("customerDefault", CustomerDefault.class);
        System.out.println(customer);
    }

    @Test
    public void autowireAutowiredByName() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AutowireConfig.class);
        CustomerByName customer = context.getBean("customerByName", CustomerByName.class);
        System.out.println(customer);
    }

    @Test
    public void autowireAutowiredByPrimary() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AutowireConfig.class);
        CustomerByPrimary customer = context.getBean("customerByPrimary", CustomerByPrimary.class);
        System.out.println(customer);
    }

    @Test
    public void autowireResourceDefault() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ResourceConfig.class);
        suncodes.opensource.autowire.annotation.resource.CustomerDefault customer =
                context.getBean("customerDefault", suncodes.opensource.autowire.annotation.resource.CustomerDefault.class);
        System.out.println(customer);
    }

    @Test
    public void autowireResourceByName() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ResourceConfig.class);
        suncodes.opensource.autowire.annotation.resource.CustomerByName customer =
                context.getBean("customerByName", suncodes.opensource.autowire.annotation.resource.CustomerByName.class);
        System.out.println(customer);
    }

    @Test
    public void autowireResourceByType() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ResourceConfig.class);
        suncodes.opensource.autowire.annotation.resource.CustomerByType customer =
                context.getBean("customerByType", suncodes.opensource.autowire.annotation.resource.CustomerByType.class);
        System.out.println(customer);
    }
}
