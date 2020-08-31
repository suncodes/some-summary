package suncodes.samples.aop;

import java.lang.reflect.Method;

import suncodes.aop.advice.AfterReturningAdvice;

public class MyAfterReturningAdvice implements AfterReturningAdvice {

	@Override
	public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
		System.out.println(this + " 对 " + target + " 做了后置增强，得到的返回值=" + returnValue);
	}

}
