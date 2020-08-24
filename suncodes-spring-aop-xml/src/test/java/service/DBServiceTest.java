package service;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DBServiceTest {

    @Test
    public void f() {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("spring-aop-xml.xml");
        DBService dbService = context.getBean("dbService", DBService.class);
        dbService.mysql();
    }
}