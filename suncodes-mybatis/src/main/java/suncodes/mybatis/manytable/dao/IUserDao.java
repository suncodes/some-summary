package suncodes.mybatis.manytable.dao;

import suncodes.mybatis.manytable.domain.User;
import suncodes.mybatis.manytable.domain.UserAccount;

import java.util.List;

public interface IUserDao {
    /**
     * 查询所有操作
     * @return 用户列表
     */
    List<User> findAll();

    /**
     * 一对多
     * @return
     */
    List<UserAccount> selectUserAccount();
}
