package suncodes.advicedetail.aspects.tests;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import suncodes.advicedetail.aspects.interfaces.IUserDao;

public class AspectJDKTest {
    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext ac =
                new AnnotationConfigApplicationContext("suncodes.advicedetail.aspects");
        Object userDao = ac.getBean("userDao");
        if (userDao instanceof IUserDao) {
            ((IUserDao) userDao).telegram();
        }
    }
}
