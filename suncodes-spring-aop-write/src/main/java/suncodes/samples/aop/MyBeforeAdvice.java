package suncodes.samples.aop;

import java.lang.reflect.Method;

import suncodes.aop.advice.MethodBeforeAdvice;

public class MyBeforeAdvice implements MethodBeforeAdvice {

	@Override
	public void before(Method method, Object[] args, Object target) throws Throwable {
		System.out.println(this + " 对 " + target + " 进行了前置增强！");
	}

}
