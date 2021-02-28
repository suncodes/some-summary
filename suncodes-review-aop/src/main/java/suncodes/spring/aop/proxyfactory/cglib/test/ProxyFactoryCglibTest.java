package suncodes.spring.aop.proxyfactory.cglib.test;

import org.springframework.aop.framework.ProxyFactory;
import suncodes.spring.aop.proxyfactory.cglib.advice.LogAdvice;
import suncodes.spring.aop.proxyfactory.cglib.targets.UserDao;

public class ProxyFactoryCglibTest {
    public static void main(String[] args) {
        ProxyFactory proxyFactory = new ProxyFactory();
        // 设置目标类
        UserDao userDao = new UserDao();
        proxyFactory.setTarget(userDao);
        // 设置通知
        LogAdvice logAdvice = new LogAdvice();
        proxyFactory.addAdvice(logAdvice);
        // 获取代理
        Object proxy = proxyFactory.getProxy();
        if (proxy instanceof UserDao) {
            ((UserDao) proxy).telegram();
        }
    }
}
