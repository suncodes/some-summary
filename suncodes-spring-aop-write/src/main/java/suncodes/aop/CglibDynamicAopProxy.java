package suncodes.aop;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import suncodes.aop.advisor.Advisor;
import suncodes.beans.BeanDefinition;
import suncodes.beans.BeanFactory;
import suncodes.beans.DefaultBeanFactory;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * 
 * @Description: cglib动态AOP代理实现
 * @author leeSmall
 * @date 2018年12月2日
 *
 */
public class CglibDynamicAopProxy implements AopProxy, MethodInterceptor {
	private static final Log logger = LogFactory.getLog(CglibDynamicAopProxy.class);
	private static Enhancer enhancer = new Enhancer();

	private String beanName;
	private Object target;

	private List<Advisor> matchAdvisors;

	private BeanFactory beanFactory;

	public CglibDynamicAopProxy(String beanName, Object target, List<Advisor> matchAdvisors, BeanFactory beanFactory) {
		super();
		this.beanName = beanName;
		this.target = target;
		this.matchAdvisors = matchAdvisors;
		this.beanFactory = beanFactory;
	}

	//创建代理对象
	@Override
	public Object getProxy() {
		return this.getProxy(target.getClass().getClassLoader());
	}

	//创建代理对象
	@Override
	public Object getProxy(ClassLoader classLoader) {
		if (logger.isDebugEnabled()) {
			logger.debug("为" + target + "创建cglib代理。");
		}
		Class<?> superClass = this.target.getClass();
		enhancer.setSuperclass(superClass);
		enhancer.setInterfaces(this.getClass().getInterfaces());
		enhancer.setCallback(this);
		Constructor<?> constructor = null;
		try {
			constructor = superClass.getConstructor(new Class<?>[] {});
		} catch (NoSuchMethodException | SecurityException e) {

		}
		if (constructor != null) {
			return enhancer.create();
		} 
		//没有无参构造函数时,从BeanDefinition里面获取构造参数的类型和值进行增强
		else {
			BeanDefinition bd = ((DefaultBeanFactory) beanFactory).getBeanDefinition(beanName);
			return enhancer.create(bd.getConstructor().getParameterTypes(), bd.getConstructorArgumentRealValues());
		}
	}

	/**
	 * TODO 在代理对象调用自己的方法的时候，调用
	 * @param proxy
	 * @param method
	 * @param args
	 * @param methodProxy
	 * @return
	 * @throws Throwable
	 */
	@Override
	public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
		return AopProxyUtils.applyAdvices(target, method, args, matchAdvisors, proxy, beanFactory);
	}

}
