package suncodes.spring.aop.proxyfactorybean.jdk.targets;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import suncodes.spring.aop.proxyfactorybean.jdk.interfaces.IUserDao;

/**
 * TODO 注册成一个bean
 */
@Service
@Slf4j
public class UserDao implements IUserDao {
    @Override
    public void telegram() {
        log.info("这是使用proxyFactoryBean的目标方法");
    }
}
