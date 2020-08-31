package suncodes.aop;

import java.util.List;

import suncodes.aop.advisor.Advisor;
import suncodes.beans.BeanFactory;

/**
 * 
 * @Description: 工厂AopProxyFactory负责选择使用哪个动态代理
 * @author leeSamll
 * @date 2018年12月3日
 *
 */
public interface AopProxyFactory {

	AopProxy createAopProxy(Object bean, String beanName, List<Advisor> matchAdvisors, BeanFactory beanFactory)
			throws Throwable;

	/**
	 * 获得默认的AopProxyFactory实例
	 * 
	 * @return AopProxyFactory
	 */
	static AopProxyFactory getDefaultAopProxyFactory() {
		return new DefaultAopProxyFactory();
	}
}
