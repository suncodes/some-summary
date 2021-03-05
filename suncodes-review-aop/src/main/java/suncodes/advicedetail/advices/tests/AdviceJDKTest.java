package suncodes.advicedetail.advices.tests;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import suncodes.advicedetail.advices.interfaces.IUserDao;

public class AdviceJDKTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ac =
                new AnnotationConfigApplicationContext("suncodes.advicedetail.advices");
        IUserDao bean = ac.getBean("proxyFactoryBean", IUserDao.class);
        bean.telegram();
    }
}
