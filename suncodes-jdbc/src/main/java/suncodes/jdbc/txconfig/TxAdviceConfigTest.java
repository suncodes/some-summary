package suncodes.jdbc.txconfig;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import suncodes.jdbc.txconfig.service.TxAdviceService;

public class TxAdviceConfigTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(TxAdviceConfig.class);
        TxAdviceService txAdviceService = context.getBean("txAdviceService", TxAdviceService.class);
        txAdviceService.add();
    }
}
