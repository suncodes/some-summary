package suncodes.advicedetail.aspects.targets;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import suncodes.advicedetail.aspects.interfaces.IUserDao;

@Slf4j
@Service
public class UserDao implements IUserDao {
    @Override
    public void telegram() throws Exception {
        log.info("advice target method");
        throw new Exception("异常");
    }
}
