package suncodes.dyanmic.proxy.cglib.targets;

import lombok.extern.slf4j.Slf4j;

/**
 * 2、目标类（被代理类）
 *
 * @author sunchuizhe
 */
@Slf4j
public class UserDao {

    public void telegram() {
        log.info("cglib动态代理---目标类");
    }
}
