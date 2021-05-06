package suncodes.mybatis.spring.dao.impl;

import org.apache.ibatis.session.SqlSession;
import suncodes.mybatis.spring.dao.IUserDao;
import suncodes.mybatis.spring.domain.User;

import java.util.List;

public class UserDaoImpl implements IUserDao {

    private SqlSession sqlSession;

    public void setSqlSession(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public List<User> findAll() {
        return sqlSession.selectList("suncodes.mybatis.spring.dao.IUserDao.findAll");
    }
}
