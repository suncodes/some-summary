package suncodes.mybatis.impl.dao;

import suncodes.mybatis.impl.domain.User;

import java.util.List;

public interface IUserDao {
    /**
     * 查询所有操作
     * @return 用户列表
     */
    List<User> findAll();
}
