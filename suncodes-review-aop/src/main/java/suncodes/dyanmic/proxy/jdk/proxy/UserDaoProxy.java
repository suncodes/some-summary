package suncodes.dyanmic.proxy.jdk.proxy;

import java.lang.reflect.Proxy;

/**
 * 4、代理类
 *
 * @author sunchuizhe
 */
public class UserDaoProxy {

    /**
     * 目标类实例
     */
    private Object targetObject;

    public UserDaoProxy(Object targetObject) {
        this.targetObject = targetObject;
    }

    /**
     * 创建代理实例
     * 三个参数：
     * 1、类加载器
     * 2、目标类实现的接口
     * 3、对应的代理类处理器
     *
     * @return 代理类实例
     */
    public Object getProxyInstance() {
        return Proxy.newProxyInstance(UserDaoProxy.class.getClassLoader(),
                targetObject.getClass().getInterfaces(),
                new UserDaoProxyHandler(targetObject));
    }
}
