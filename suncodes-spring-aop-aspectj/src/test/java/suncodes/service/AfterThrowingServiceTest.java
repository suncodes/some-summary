package suncodes.service;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.*;

public class AfterThrowingServiceTest {

    @Test
    public void f() {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("aop.xml");
        AfterThrowingService afterThrowingService =
                context.getBean("afterThrowingService", AfterThrowingService.class);

        afterThrowingService.f();
    }
}