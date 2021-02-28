package suncodes.spring.aop.proxyfactory.cglib.targets;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserDao {
    public void telegram() {
        log.info("cglib代理，proxyFactory目标方法");
    }
}
