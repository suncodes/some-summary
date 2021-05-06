package suncodes.mybatis.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import suncodes.mybatis.springboot.dao.IUserDao;
import suncodes.mybatis.springboot.domain.User;

import java.util.List;

@SpringBootTest
class SuncodesMybatisSpringbootApplicationTests {

    @Autowired
    private IUserDao userDao;

    @Test
    void contextLoads() {
        List<User> users = userDao.findAll();
        for (User user : users) {
            System.out.println(user);
        }
    }
}
