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

    @Test
    public void f1() {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("spring-aop1.xml");
        DBService dbService = context.getBean("dbService", DBService.class);
        dbService.mysql();
        System.out.println("===");
    }

    @Test
    public void f2() {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("spring-aop2.xml");
        DBService dbService = context.getBean("dbService", DBService.class);
        dbService.mysql();
        System.out.println("===");
    }

    @Test
    public void f3() {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("spring-aop3.xml");
        DBService dbService = context.getBean("dbService", DBService.class);
        dbService.mysql();
        System.out.println("===");
    }
}