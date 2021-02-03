package suncodes.dyanmic.proxy.cglib.test;

import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.core.DebuggingClassWriter;
import suncodes.dyanmic.proxy.cglib.proxy.UserDaoProxy;
import suncodes.dyanmic.proxy.cglib.targets.UserDao;

/**
 * 5、CGLIB动态代理测试类
 * @author sunchuizhe
 */
@Slf4j
public class CglibDyanmicProxyTest {

    public static void main(String[] args) {
        // cglib动态代理持久化为.class文件配置方式
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "suncodes.dyanmic.proxy.cglib.targets");
        UserDao userDao = new UserDao();
        UserDao proxyInstance = (UserDao)new UserDaoProxy(userDao).getProxyInstance();
        // 这里获取的对象和Handler第一个参数是一个对象
        log.info("测试：{}", proxyInstance.getClass().getName());
        proxyInstance.telegram();
    }
}
