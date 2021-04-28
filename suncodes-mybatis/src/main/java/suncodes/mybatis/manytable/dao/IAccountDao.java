package suncodes.mybatis.manytable.dao;

import suncodes.mybatis.manytable.domain.Account;
import suncodes.mybatis.manytable.domain.AccountUser;
import suncodes.mybatis.manytable.domain.User;

import java.util.List;

public interface IAccountDao {
    /**
     * 查询所有操作
     * @return 账号列表
     */
    List<Account> findAll();

    List<AccountUser> selectAccountUser();
}
