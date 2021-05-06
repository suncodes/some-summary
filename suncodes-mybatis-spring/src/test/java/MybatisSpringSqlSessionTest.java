import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import suncodes.mybatis.spring.dao.IUserDao;
import suncodes.mybatis.spring.domain.User;

import java.util.List;

public class MybatisSpringSqlSessionTest {

    @Test
    public void f() {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("MybatisSpringSqlSession.xml");
        IUserDao userDao = context.getBean("userDao", IUserDao.class);
        List<User> users = userDao.findAll();
        for (User user : users) {
            System.out.println(user);
        }
    }
}
