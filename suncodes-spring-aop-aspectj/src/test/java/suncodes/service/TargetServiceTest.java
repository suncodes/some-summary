package suncodes.service;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TargetServiceTest {

    @Test
    public void f() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("aop.xml");
        TargetService targetService = context.getBean("targetService", TargetService.class);
        targetService.f();
    }
}