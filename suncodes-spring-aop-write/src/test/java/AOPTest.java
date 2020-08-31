import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import suncodes.aop.AdvisorAutoProxyCreator;
import suncodes.aop.advisor.AspectJPointcutAdvisor;
import suncodes.beans.BeanReference;
import suncodes.beans.GenericBeanDefinition;
import suncodes.beans.PreBuildBeanFactory;
import suncodes.samples.ABean;
import suncodes.samples.CBean;
import suncodes.samples.aop.MyAfterReturningAdvice;
import suncodes.samples.aop.MyBeforeAdvice;
import suncodes.samples.aop.MyMethodInterceptor;

public class AOPTest {

	static PreBuildBeanFactory bf = new PreBuildBeanFactory();

	@Test
	public void testCirculationDI() throws Throwable {

		GenericBeanDefinition bd = new GenericBeanDefinition();
		bd.setBeanClass(ABean.class);
		List<Object> args = new ArrayList<>();
		args.add("abean01");
		args.add(new BeanReference("cbean"));
		bd.setConstructorArgumentValues(args);
		bf.registerBeanDefinition("abean", bd);

		bd = new GenericBeanDefinition();
		bd.setBeanClass(CBean.class);
		args = new ArrayList<>();
		args.add("cbean01");
		bd.setConstructorArgumentValues(args);
		bf.registerBeanDefinition("cbean", bd);

		// 前置增强advice bean注册
		bd = new GenericBeanDefinition();
		bd.setBeanClass(MyBeforeAdvice.class);
		bf.registerBeanDefinition("myBeforeAdvice", bd);

		// 环绕增强advice bean注册
		bd = new GenericBeanDefinition();
		bd.setBeanClass(MyMethodInterceptor.class);
		bf.registerBeanDefinition("myMethodInterceptor", bd);

		// 后置增强advice bean注册
		bd = new GenericBeanDefinition();
		bd.setBeanClass(MyAfterReturningAdvice.class);
		bf.registerBeanDefinition("myAfterReturningAdvice", bd);

		// 往BeanFactory中注册AOP的BeanPostProcessor
		AdvisorAutoProxyCreator aapc = new AdvisorAutoProxyCreator();
		bf.registerBeanPostProcessor(aapc);
		// 向AdvisorAutoProxyCreator注册Advisor
		aapc.registAdvisor(
				new AspectJPointcutAdvisor("myBeforeAdvice", "execution(* suncodes.samples.ABean.*(..))"));
		// 向AdvisorAutoProxyCreator注册Advisor
		aapc.registAdvisor(
				new AspectJPointcutAdvisor("myMethodInterceptor", "execution(* suncodes.samples.ABean.do*(..))"));
		// 向AdvisorAutoProxyCreator注册Advisor
		aapc.registAdvisor(new AspectJPointcutAdvisor("myAfterReturningAdvice",
				"execution(* suncodes.samples.ABean.do*(..))"));

		bf.preInstantiateSingletons();

		ABean abean = (ABean) bf.getBean("abean");

		abean.doSomthing();
		System.out.println("--------------------------------");
		abean.sayHello();
	}
}
