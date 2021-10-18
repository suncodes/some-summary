package suncodes.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import suncodes.admin.bean.User;
import suncodes.admin.mapper.UserMapper;
import suncodes.admin.service.UserService;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


}
