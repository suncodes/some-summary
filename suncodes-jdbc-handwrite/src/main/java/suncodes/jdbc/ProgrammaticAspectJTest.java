package suncodes.jdbc;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import suncodes.jdbc.service.ResourceAspectjService;

public class ProgrammaticAspectJTest {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("jdbctemplate.xml");
        ResourceAspectjService resourceService =
                context.getBean("resourceAspectjService", ResourceAspectjService.class);
        resourceService.add();
    }
}
