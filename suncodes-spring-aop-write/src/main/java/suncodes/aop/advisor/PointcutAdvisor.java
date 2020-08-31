package suncodes.aop.advisor;

import suncodes.aop.pointcut.Pointcut;

/**
 * 
 * @Description:基于切入点的通知者实现
 * @author leeSmall
 * @date 2018年12月2日
 *
 */
public interface PointcutAdvisor extends Advisor {

	Pointcut getPointcut();
}
