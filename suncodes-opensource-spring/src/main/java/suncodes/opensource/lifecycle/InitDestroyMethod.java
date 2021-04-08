package suncodes.opensource.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class InitDestroyMethod implements InitializingBean, DisposableBean {

    /**
     * 1、通过注解的方式进行一些初始化操作
     */
    @PostConstruct
    public void postConstruct() {
        System.out.println("@PostConstruct...");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("@PreDestroy...");
    }

    /**
     * 3、通过创建bean的时候，指定 initMethod 方法进行初始化操作
     */
    public void init() {
        System.out.println("InitDestroyMethod...init...");
    }

    public void destroy1() {
        System.out.println("InitDestroyMethod...destroy1...");
    }

    /**
     * 通过实现 InitializingBean 接口进行初始化操作
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializingBean...afterPropertiesSet...");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("DisposableBean...destroy...");
    }
}
