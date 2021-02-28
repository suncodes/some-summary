package suncodes.spring.aop.proxyfactory.jdk.test;

import org.springframework.aop.framework.ProxyFactory;
import suncodes.spring.aop.proxyfactory.jdk.advice.LogAdvice;
import suncodes.spring.aop.proxyfactory.jdk.interfaces.IUserDao;
import suncodes.spring.aop.proxyfactory.jdk.targets.UserDao;

public class ProxyFactoryJdkTest {
    public static void main(String[] args) {
        // JDK代理，默认设置target和interfaces
        ProxyFactory factory = new ProxyFactory(new UserDao());
        // 注释的三句话和上面的一句是一个含义
//        ProxyFactory factory = new ProxyFactory();
//        factory.setTarget(new UserDao());
//        factory.setInterfaces(IUserDao.class);

        // 设置Advice，会代理所有的方法
        LogAdvice logAdvice = new LogAdvice();
        factory.addAdvice(logAdvice);
        // 获取代理对象
        Object proxy = factory.getProxy();
        System.out.println(proxy.getClass().getName());
        if (proxy instanceof IUserDao) {
            ((IUserDao) proxy).telegram();
        }
    }
}
