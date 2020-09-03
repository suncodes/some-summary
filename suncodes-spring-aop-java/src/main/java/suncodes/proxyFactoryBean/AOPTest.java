package suncodes.proxyFactoryBean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import suncodes.proxyFactoryBean.target.AOPTargetImpl;
import suncodes.proxyFactoryBean.target.AOPTargetInterface;

public class AOPTest {
    public static void main(String[] args) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("proxyFactoryBean/proxyFactoryBean.xml");
        AOPTargetInterface aopTarget = context.getBean("factoryBean", AOPTargetInterface.class);
//        aopTarget.drink();
        aopTarget.eat();
    }
}
