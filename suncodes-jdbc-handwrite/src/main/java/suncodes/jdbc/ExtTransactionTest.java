package suncodes.jdbc;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import suncodes.jdbc.service.ResourceAnnotionAspectjService;
import suncodes.jdbc.service.ResourceAspectjService;

public class ExtTransactionTest {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("jdbctemplate.xml");
        ResourceAnnotionAspectjService resourceService =
                context.getBean("resourceAnnotionAspectjService", ResourceAnnotionAspectjService.class);
        resourceService.add();
    }
}
