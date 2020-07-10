package sun.ioc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.ioc.bo.UserBO;

@Service
public class UserService {

    @Autowired
    private UserBO userBO;

    public void f() {
        System.out.println(userBO);
    }
}
