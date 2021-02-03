package suncodes.statics.proxy.proxy;

import lombok.extern.slf4j.Slf4j;
import suncodes.statics.proxy.interfaces.IUserDao;
import suncodes.statics.proxy.target.UserDao;

/**
 * 3、代理类
 * 实现接口，组合目标类
 *
 * @author sunchuizhe
 */
@Slf4j
public class UserDaoProxy implements IUserDao {

    private IUserDao userDao;

    public UserDaoProxy(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void telegram() {
        log.info("静态代理---代理类");
        userDao.telegram();
    }
}
