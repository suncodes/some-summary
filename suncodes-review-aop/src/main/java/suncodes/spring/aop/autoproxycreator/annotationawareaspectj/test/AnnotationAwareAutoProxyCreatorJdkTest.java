package suncodes.spring.aop.autoproxycreator.annotationawareaspectj.test;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import suncodes.spring.aop.autoproxycreator.annotationawareaspectj.interfaces.IUserDao;

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
public class AnnotationAwareAutoProxyCreatorJdkTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ac =
                new AnnotationConfigApplicationContext("suncodes.spring.aop.autoproxycreator.annotationawareaspectj");

        // 创建自动代理类
        // TODO 不能使用此方式，不生效，需要把 AnnotationAwareAspectJAutoProxyCreator 配置成bean才行，而不能手动注册

        IUserDao userDao = ac.getBean("userDao", IUserDao.class);
        userDao.telegram();
    }
}
