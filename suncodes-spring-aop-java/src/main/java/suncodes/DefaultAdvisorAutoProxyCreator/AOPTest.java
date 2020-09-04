package suncodes.DefaultAdvisorAutoProxyCreator;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import suncodes.DefaultAdvisorAutoProxyCreator.target1.AOPTargetImpl;

public class AOPTest {
    public static void main(String[] args) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("DefaultAdvisorAutoProxyCreator/creator.xml");
        // 没有成功
        AOPTargetImpl autoProxyCreator = context.getBean("aopTarget", AOPTargetImpl.class);
        autoProxyCreator.eat();
    }
}
