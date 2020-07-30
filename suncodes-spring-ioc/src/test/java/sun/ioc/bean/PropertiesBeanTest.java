package sun.ioc.bean;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PropertiesBeanTest {
    private ClassPathXmlApplicationContext applicationContext =
            new ClassPathXmlApplicationContext("propsBean.xml");

    @Test
    public void f() {
        PropertiesBean propsBean = applicationContext.getBean("propsBean", PropertiesBean.class);
        System.out.println(propsBean);
    }
}