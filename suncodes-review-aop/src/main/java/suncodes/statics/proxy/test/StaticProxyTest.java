package suncodes.statics.proxy.test;

import suncodes.statics.proxy.proxy.UserDaoProxy;
import suncodes.statics.proxy.targets.UserDao;

/**
 * 4、静态代理测试
 *
 * @author sunchuizhe
 */
public class StaticProxyTest {
    public static void main(String[] args) {
        UserDao userDao = new UserDao();
        UserDaoProxy userDaoProxy = new UserDaoProxy(userDao);
        userDaoProxy.telegram();
    }
}
