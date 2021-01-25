package suncodes.jdbc;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import suncodes.jdbc.service.ResourceService;

public class ProgrammaticTest {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("jdbctemplate.xml");
        ResourceService resourceService = context.getBean("resourceService", ResourceService.class);
        resourceService.add();
    }
}
