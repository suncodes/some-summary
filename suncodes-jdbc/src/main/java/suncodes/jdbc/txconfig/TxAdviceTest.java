package suncodes.jdbc.txconfig;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import suncodes.jdbc.txconfig.service.TxAdviceService;

public class TxAdviceTest {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("txAdvice.xml");
        TxAdviceService txAdviceService = context.getBean("txAdviceService", TxAdviceService.class);
        txAdviceService.add();
    }
}
