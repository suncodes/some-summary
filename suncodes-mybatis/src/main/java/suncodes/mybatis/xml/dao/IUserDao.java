package suncodes.mybatis.xml.dao;

import suncodes.mybatis.xml.domain.User;

import java.util.List;

public interface IUserDao {
    /**
     * 查询所有操作
     * @return 用户列表
     */
    List<User> findAll();
}
