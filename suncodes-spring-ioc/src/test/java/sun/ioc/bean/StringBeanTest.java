package sun.ioc.bean;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public class StringBeanTest {
    @Test
    public void f() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("application.xml");
        StringBean string = applicationContext.getBean("string", StringBean.class);
        System.out.println(string);
    }

    @Test
    public void f1() {
        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("application.xml");
        StringBean string = applicationContext.getBean("stringBean", StringBean.class);
        System.out.println(string);
    }

    @Test
    public void f2() {
        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("application.xml");
        StringBean string = applicationContext.getBean("stringBean1", StringBean.class);
        System.out.println(string);
    }

    @Test
    public void f4() {
        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("application.xml");
        StringConstructorBean string = applicationContext.getBean("stringBean2", StringConstructorBean.class);
        System.out.println(string);
    }

    @Test
    public void f3() throws UnsupportedEncodingException {
        char a = '函';
        System.out.println(a);
        byte b = 'q';
        System.out.println(b);
        String csn = Charset.defaultCharset().name();
        System.out.println(csn);
        System.out.println("a".getBytes("UTF-16").length);
    }
}
