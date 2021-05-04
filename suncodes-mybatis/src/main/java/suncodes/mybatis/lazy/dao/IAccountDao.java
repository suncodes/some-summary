package suncodes.mybatis.lazy.dao;

import suncodes.mybatis.lazy.domain.Account;
import suncodes.mybatis.lazy.domain.AccountUser;

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
