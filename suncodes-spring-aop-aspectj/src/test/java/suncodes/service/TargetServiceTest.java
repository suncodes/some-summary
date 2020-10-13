package suncodes.service;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import suncodes.pojo.bo.HelloBO;

public class TargetServiceTest {

    @Test
    public void f() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("aop.xml");
        TargetService targetService = context.getBean("targetService", TargetService.class);
        targetService.f("fff");

//        System.out.println("==============================");
//        HelloBO helloBO = new HelloBO();
//        helloBO.setHello("hello");
//        helloBO.setWorld("word");
//        targetService.f1(helloBO);
//
//        System.out.println("==============================");
//        targetService.f2();
    }
}