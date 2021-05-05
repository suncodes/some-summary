package suncodes.mybatis.cache.dao;

import suncodes.mybatis.cache.domain.User;
import suncodes.mybatis.cache.domain.UserAccount;

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
