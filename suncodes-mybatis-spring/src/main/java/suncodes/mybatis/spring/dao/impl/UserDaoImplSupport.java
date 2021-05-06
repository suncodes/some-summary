package suncodes.mybatis.spring.dao.impl;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import suncodes.mybatis.spring.dao.IUserDao;
import suncodes.mybatis.spring.domain.User;

import java.util.List;

public class UserDaoImplSupport extends SqlSessionDaoSupport implements IUserDao {
    @Override
    public List<User> findAll() {
        return getSqlSession().selectList("suncodes.mybatis.spring.dao.IUserDao.findAll");
    }
}
