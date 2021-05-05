package suncodes.mybatis.cache.dao;

import suncodes.mybatis.cache.domain.Account;
import suncodes.mybatis.cache.domain.AccountUser;

import java.util.List;

public interface IAccountDao {
    /**
     * 查询所有操作
     * @return 账号列表
     */
    List<Account> findAll();

    List<AccountUser> selectAccountUser();

    List<Account> selectByUserId(Integer userId);

    List<AccountUser> selectAccountUserLazy();
}
