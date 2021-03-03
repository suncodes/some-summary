package suncodes.spring.aop.proxyfactorybean.jdk.test;

import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import suncodes.spring.aop.proxyfactorybean.jdk.interfaces.IUserDao;

/**
 * 通过这个，你应该能想到其变种形式
 * 1. xml
 * 2. java config
 * 不然白学了
 * @author sunchuizhe
 */
public class ProxyFactoryBeanJdkTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ac =
                new AnnotationConfigApplicationContext("suncodes.spring.aop.proxyfactorybean.jdk");

        // aop3  实际的spring使用aop的过程，配置好ProxyFactoryBean，给ProxyFactoryBean设置一个bean id
        // 然后通过ac.getBean(bean id),就取得被ProxyFactoryBean代理的对象，不是ProxyFactoryBean
        // 因为这个bean id虽然代表ProxyFactoryBean对象，直接getBean获取的是ProxyFactoryBean.getObject()返回的对象，即代理对象
        // ac.getBean(&bean id),才能取得ProxyFactoryBean对象

        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setBeanFactory(ac.getBeanFactory());
        // LogAdvice和目标类UserDao已经被注册为bean
        // aop拦截处理类
        proxyFactoryBean.setInterceptorNames("logAdvice");
        // 代理的接口
        proxyFactoryBean.setInterfaces(IUserDao.class);
        // 被代理对象
        proxyFactoryBean.setTarget(ac.getBean(IUserDao.class));
        // 放入bean工厂，实际开发是在config下使用注解，设置多个proxyFactoryBean代理，设置不同bean id
        ac.getBeanFactory().registerSingleton("myProxy", proxyFactoryBean);

        // TODO 注意是怎么获取代理对象的
        IUserDao iUserDao = (IUserDao) ac.getBean("myProxy");
        iUserDao.telegram();
        // 获取直接的ProxyFactoryBean对象，加&
        System.out.println(ac.getBean("&myProxy"));
    }
}
