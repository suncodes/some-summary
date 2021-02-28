package suncodes.spring.aop.proxyfactory.jdk.targets;

import lombok.extern.slf4j.Slf4j;
import suncodes.spring.aop.proxyfactory.jdk.interfaces.IUserDao;

@Slf4j
public class UserDao implements IUserDao {
    @Override
    public void telegram() {
        log.info("这是使用proxyFactory的目标方法");
    }
}
