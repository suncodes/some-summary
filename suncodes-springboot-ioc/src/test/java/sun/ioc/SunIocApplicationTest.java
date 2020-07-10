package sun.ioc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import sun.ioc.service.UserService;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class SunIocApplicationTest {

    @Autowired
    private UserService userService;

    @Test
    public void f() {
        userService.f();
    }
}