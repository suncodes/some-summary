package suncodes.DefaultAdvisorAutoProxyCreator;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import suncodes.DefaultAdvisorAutoProxyCreator.target1.AOPTargetImpl;
import suncodes.DefaultAdvisorAutoProxyCreator.target1.AOPTargetInterface;
import suncodes.DefaultAdvisorAutoProxyCreator.target2.SunImpl;
import suncodes.DefaultAdvisorAutoProxyCreator.target2.SunInterface;

public class AOPTest {
    public static void main(String[] args) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("DefaultAdvisorAutoProxyCreator/creator.xml");
        // 没有成功，因为通知必须是advisor类型的，不能是advice接口。
        AOPTargetInterface autoProxyCreator = context.getBean("aopTarget", AOPTargetInterface.class);
        autoProxyCreator.eat();

        ApplicationContext context1 =
                new ClassPathXmlApplicationContext("DefaultAdvisorAutoProxyCreator/aop.xml");
        SunInterface sun = context1.getBean("sun", SunInterface.class);
        sun.inter();
    }
}
