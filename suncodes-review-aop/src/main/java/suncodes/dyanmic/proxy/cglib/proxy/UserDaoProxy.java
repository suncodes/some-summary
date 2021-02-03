package suncodes.dyanmic.proxy.cglib.proxy;


import net.sf.cglib.proxy.Enhancer;

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
     * ；两个参数：
     * 1、目标类
     * 2、对应的代理类处理器
     *
     * @return 代理类实例
     */
    public Object getProxyInstance() {
        return Enhancer.create(targetObject.getClass(), new UserDaoProxyHandler(targetObject));
    }
}
