package suncodes.spring.aop.autoproxycreator.annotationawareaspectj.targets;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import suncodes.spring.aop.autoproxycreator.annotationawareaspectj.interfaces.IUserDao;

/**
 * TODO 注册成一个bean
 */
@Service
@Slf4j
public class UserDao implements IUserDao {
    @Override
    public void telegram() {
        log.info("这是使用 AnnotationAwareAspectJAutoProxyCreator 的目标方法");
    }
}
