import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sun.ioc.bo.UserBO;
import sun.ioc.service.UserService;

public class SpringIocTest {

    @Test
    public void f() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application.xml");
        UserBO user = context.getBean("user", UserBO.class);
        System.out.println(user);
        UserService userService = context.getBean("userService", UserService.class);
        userService.f();
    }
}
