package sun.ioc.bean;


import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class FloatBeanTest {

    @Test
    public void f() {
        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("application.xml");
        FloatBean aFloat = applicationContext.getBean("aFloat", FloatBean.class);
        System.out.println(aFloat);
    }

    @Test
    public void f1() {
        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("application.xml");
        FloatConstructorBean aFloat = applicationContext.getBean("floatConstructorBean", FloatConstructorBean.class);
        System.out.println(aFloat);
    }
}