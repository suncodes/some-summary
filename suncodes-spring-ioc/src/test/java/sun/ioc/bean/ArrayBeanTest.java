package sun.ioc.bean;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ArrayBeanTest {

    private ClassPathXmlApplicationContext applicationContext =
            new ClassPathXmlApplicationContext("arrayBean.xml");

    @Test
    public void f() {
        ArrayBean arrayBean = applicationContext.getBean("arrayBean", ArrayBean.class);
        System.out.println(arrayBean);
    }
}