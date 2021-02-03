package suncodes.dyanmic.proxy.jdk.test;

import lombok.extern.slf4j.Slf4j;
import suncodes.dyanmic.proxy.jdk.interfaces.IUserDao;
import suncodes.dyanmic.proxy.jdk.proxy.UserDaoProxy;
import suncodes.dyanmic.proxy.jdk.targets.UserDao;

/**
 * 5、JDK动态代理测试类
 * @author sunchuizhe
 */
@Slf4j
public class JdkDyanmicProxyTest {

    public static void main(String[] args) {
        // java动态代理持久化为.class文件配置方式
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        UserDao userDao = new UserDao();
        IUserDao proxyInstance = (IUserDao)new UserDaoProxy(userDao).getProxyInstance();
        // 这里获取的对象和Handler第一个参数是一个对象
        log.info("测试：{}", proxyInstance.getClass().getName());
        proxyInstance.telegram();
    }
}
