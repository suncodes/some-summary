package suncodes.jdbc.tx;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test05 {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("tx.xml");
        Test04 test04 = context.getBean("test04", Test04.class);
        test04.insert();
    }
}
