package service;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DBServiceTest {

    @Test
    public void f() {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("spring-aop-xml.xml");
        DBService dbService = context.getBean("dbService", DBService.class);
        DBService dbService1 = context.getBean("dbService1", DBService.class);
        dbService.mysql();
        dbService1.mysql();
        System.out.println("===");
    }
}