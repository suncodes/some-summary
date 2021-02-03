package suncodes.dyanmic.proxy.cglib.proxy;

import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.InvocationHandler;

import java.lang.reflect.Method;

/**
 * 3、代理 Handler
 *
 * @author sunchuizhe
 */
@Slf4j
public class UserDaoProxyHandler implements InvocationHandler {

    /**
     * 被代理类的实例，通过传参赋值
     */
    private Object targetObject;

    public UserDaoProxyHandler(Object targetObject) {
        this.targetObject = targetObject;
    }

    /**
     * 具体代理的逻辑
     *
     * @param proxy  动态代理类的一个实例（就是在内存中生成的代理类）
     * @param method 被代理类的方法
     * @param args   方法对应的参数
     * @return 函数返回值
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("cglib动态代理---代理Handler: {}", proxy.getClass().getName());
        method.invoke(targetObject, args);
        return null;
    }
}
