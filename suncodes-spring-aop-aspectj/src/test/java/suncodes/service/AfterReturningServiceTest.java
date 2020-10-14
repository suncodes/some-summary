package suncodes.service;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.*;

public class AfterReturningServiceTest {

    @Test
    public void f() {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("aop.xml");
        AfterReturningService afterReturningService =
                context.getBean("afterReturningService", AfterReturningService.class);

        afterReturningService.f("------------", ">>>>>>");
    }
}