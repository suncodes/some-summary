package suncodes.jdbc;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import suncodes.jdbc.service.TransactionTemplateService;

/**
 * 使用TransactionTemplate进行事务管理
 */
public class TransactionTemplateTest {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("transaction-template.xml");

        TransactionTemplateService transactionTemplateService =
                context.getBean("transactionTemplateService", TransactionTemplateService.class);
        transactionTemplateService.f();
    }
}
