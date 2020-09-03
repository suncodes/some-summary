package suncodes.proxyFactory;

import org.springframework.aop.framework.ProxyFactory;
import suncodes.proxyFactory.advice.AOPAdvice;
import suncodes.proxyFactory.target.AOPTargetImpl;
import suncodes.proxyFactory.target.AOPTargetInterface;

public class AOPTest {
    public static void main(String[] args) {
        AOPTargetInterface aopTargetInterface = new AOPTargetImpl();
        AOPAdvice aopAdvice = new AOPAdvice();
        ProxyFactory pf = new ProxyFactory();
        pf.setTarget(aopTargetInterface);
        pf.addAdvice(aopAdvice);
        Object o = pf.getProxy();
        if (o instanceof AOPTargetImpl) {
            ((AOPTargetImpl) o).drink();
            ((AOPTargetImpl) o).eat();
        }
    }
}
