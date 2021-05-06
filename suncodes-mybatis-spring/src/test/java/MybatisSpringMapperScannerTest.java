import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import suncodes.mybatis.spring.config.MapperScannerConfig;
import suncodes.mybatis.spring.dao.mapperscan.IUserDao;
import suncodes.mybatis.spring.domain.User;

import java.util.List;

public class MybatisSpringMapperScannerTest {

    @Test
    public void f() {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(MapperScannerConfig.class);
        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }
        IUserDao userDao = context.getBean("IUserDao", IUserDao.class);
        List<User> users = userDao.findAll();
        for (User user : users) {
            System.out.println(user);
        }
    }
}
