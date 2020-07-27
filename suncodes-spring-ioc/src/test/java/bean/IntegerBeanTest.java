package bean;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sun.ioc.bean.IntegerBean;
import sun.ioc.bean.IntegerConstructorBean;

public class IntegerBeanTest {
    @Test
    public void f() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("application.xml");
        IntegerBean intIntUnPackage = applicationContext.getBean("intIntUnPackage", IntegerBean.class);
        System.out.println(intIntUnPackage);
    }

    @Test
    public void f1() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("application.xml");
        IntegerConstructorBean intIntPackageConstructor = applicationContext.getBean("intIntPackageConstructor", IntegerConstructorBean.class);
        System.out.println(intIntPackageConstructor);
    }
}
