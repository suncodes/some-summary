package suncodes.aspectj.annotation.test;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import suncodes.aspectj.annotation.interfaces.IUserDao;

/**
 *  我们知道切面Advisor是切点和增强的复合体，Advisor本身已经包含了足够的信息，如横切逻辑及连接点。
 *
 *  DefaultAdvisorAutoProxyCreator能够扫描Advisor,
 *  并将Advisor自动织入匹配的目标Bean中，即为匹配的目标Bean自动创建代理。
 *
 * 1. xml
 * 2. java config
 * 不然白学了
 * @author sunchuizhe
 */
public class AnnotationAspectJdkTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ac =
                new AnnotationConfigApplicationContext("suncodes.aspectj.annotation");

        IUserDao userDao = ac.getBean("userDao", IUserDao.class);
        userDao.telegram();
    }
}
