package suncodes.proxyFactory;

import org.springframework.aop.framework.ProxyFactory;
import suncodes.proxyFactory.advice.AOPAdvice;
import suncodes.proxyFactory.target1.AOPTargetImpl;
import suncodes.proxyFactory.target1.AOPTargetInterface;

public class AOPTest {
    public static void main(String[] args) {
        AOPTargetInterface aopTargetInterface = new AOPTargetImpl();
        AOPAdvice aopAdvice = new AOPAdvice();
        ProxyFactory pf = new ProxyFactory();
        // 设置了代理接口，使用JDK代理
        pf.addInterface(AOPTargetInterface.class);
        pf.setTarget(aopTargetInterface);
        pf.addAdvice(aopAdvice);
        Object o = pf.getProxy();
        // 需要使用接口，因为生成的是接口的实现类
        if (o instanceof AOPTargetInterface) {
            ((AOPTargetInterface) o).eat();
        }

        System.out.println("===================");
        AOPTargetInterface aopTargetInterface1 = new AOPTargetImpl();
        AOPAdvice aopAdvice1 = new AOPAdvice();
        ProxyFactory pf1 = new ProxyFactory();
        // 只设置了目标类，CGLIB代理
        pf1.setTarget(aopTargetInterface1);
        pf1.addAdvice(aopAdvice1);
        Object o1 = pf1.getProxy();
        // 生成目标类独栋子类
        if (o1 instanceof AOPTargetImpl) {
            ((AOPTargetImpl) o1).eat();
            ((AOPTargetImpl) o1).drink();
        }
    }
}
