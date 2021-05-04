package suncodes.mybatis.lazy.dao;

import suncodes.mybatis.lazy.domain.User;
import suncodes.mybatis.lazy.domain.UserAccount;

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

    List<User> selectById(Integer id);

    List<UserAccount> selectUserAccountLazy();
}
