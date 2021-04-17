package suncodes.mybatis.impl.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import suncodes.mybatis.impl.dao.IUserDao;
import suncodes.mybatis.impl.domain.User;

import java.util.List;

public class UserDaoImpl implements IUserDao {

    private SqlSessionFactory sqlSessionFactory;

    public UserDaoImpl(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    public List<User> findAll() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<User> users = sqlSession.selectList("suncodes.mybatis.impl.dao.IUserDao.findAll");
        sqlSession.close();
        return users;
    }
}
