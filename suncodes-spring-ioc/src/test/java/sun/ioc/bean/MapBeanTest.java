package sun.ioc.bean;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.*;

public class MapBeanTest {

    private ClassPathXmlApplicationContext applicationContext =
            new ClassPathXmlApplicationContext("mapBean.xml");

    @Test
    public void f() {
        MapBean mapBean = applicationContext.getBean("mapBean", MapBean.class);
        System.out.println(mapBean);
    }
}