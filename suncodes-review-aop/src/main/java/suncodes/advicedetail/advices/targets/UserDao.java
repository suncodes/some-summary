package suncodes.advicedetail.advices.targets;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import suncodes.advicedetail.advices.interfaces.IUserDao;

@Slf4j
@Service
public class UserDao implements IUserDao {
    @Override
    public void telegram() {
        log.info("advice target method");
    }
}
