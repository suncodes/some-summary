import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import suncodes.mybatis.spring.dao.mapperscan.IUserDao;
import suncodes.mybatis.spring.domain.User;

import java.util.List;

/**
 * &lt; mybatis:scan base-package="suncodes.mybatis.spring.dao.mapperscan"/&gt;
 */
public class MybatisSpringScanXmlTest {

    @Test
    public void f() {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("MybatisSpringScan.xml");
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
