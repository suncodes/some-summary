package suncodes.spring.aop.autoproxycreator.defaultadvisor.test;

import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import suncodes.spring.aop.autoproxycreator.defaultadvisor.interfaces.IUserDao;

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
public class DefaultAdvisorAutoProxyCreatorJdkTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ac =
                new AnnotationConfigApplicationContext("suncodes.spring.aop.autoproxycreator.defaultadvisor");

        // 创建自动代理类
        // TODO 不能使用此方式，不生效，需要把 DefaultAdvisorAutoProxyCreator 配置成bean才行，而不能手动注册
//        DefaultAdvisorAutoProxyCreator d = new DefaultAdvisorAutoProxyCreator();
//        ac.getBeanFactory().registerSingleton("defaultAdvisorAutoProxyCreator", d);

        IUserDao userDao = ac.getBean("userDao", IUserDao.class);
        userDao.telegram();
    }
}
