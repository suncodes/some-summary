package sun.ioc.bean;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ObjectBeanTest {

    private ClassPathXmlApplicationContext applicationContext =
            new ClassPathXmlApplicationContext("objectBean.xml");

    @Test
    public void f() {
        ObjectBean objectBean = applicationContext.getBean("objectBean", ObjectBean.class);
        System.out.println(objectBean);
    }
}