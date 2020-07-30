package sun.ioc.bean;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Properties;

public class ListBeanTest {

    private ClassPathXmlApplicationContext applicationContext =
            new ClassPathXmlApplicationContext("listBean.xml");

    @Test
    public void f() {
        ListBean listBean = applicationContext.getBean("listBean", ListBean.class);
        System.out.println(listBean);
    }
}