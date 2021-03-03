package suncodes.spring.aop.autoproxycreator.beanname.test;

import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import suncodes.spring.aop.autoproxycreator.beanname.interfaces.IUserDao;

/**
 * 通过这个，你应该能想到其变种形式
 * 1. xml
 * 2. java config
 * 不然白学了
 * @author sunchuizhe
 */
public class BeanNameAutoProxyCreatorJdkTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ac =
                new AnnotationConfigApplicationContext("suncodes.spring.aop.autoproxycreator.beanname");

        // 创建自动代理类
        // TODO 不能使用此方式，不生效，需要把BeanNameAutoProxyCreator配置成bean才行，而不能手动注册
        // BeanNameAutoProxyCreator proxyCreator = new BeanNameAutoProxyCreator();
        // proxyCreator.setBeanNames("userDao");
        // proxyCreator.setInterceptorNames("logAdvice");
        //  ac.getBeanFactory().registerSingleton("beanNameAutoProxyCreator", proxyCreator);

        IUserDao userDao = ac.getBean("userDao", IUserDao.class);
        userDao.telegram();
    }
}
