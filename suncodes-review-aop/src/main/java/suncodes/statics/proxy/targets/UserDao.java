package suncodes.statics.proxy.targets;

import lombok.extern.slf4j.Slf4j;
import suncodes.statics.proxy.interfaces.IUserDao;

/**
 * 2、目标类（被代理类）
 *
 * @author sunchuizhe
 */
@Slf4j
public class UserDao implements IUserDao {

    @Override
    public void telegram() {
        log.info("静态代理---目标类");
    }
}
