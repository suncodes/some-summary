package mybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import suncodes.mybatis.cache.dao.IUserDao;
import suncodes.mybatis.cache.domain.User;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MybatisCacheTest {
    private InputStream resourceAsStream;
    private SqlSessionFactory sqlSessionFactory;
    private SqlSession sqlSession;

    @Before
    public void init() throws IOException {
        // 1、读取配置文件
        resourceAsStream = Resources.getResourceAsStream("mybatis/SqlMapConfigCache.xml");
        // 2、创建 SqlSessionFactory 工厂
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        // 3、创建 SqlSession 对象
        sqlSession = sqlSessionFactory.openSession();
    }

    @After
    public void destroy() throws IOException {
        sqlSession.commit();
        // 6、释放资源
        sqlSession.close();
        resourceAsStream.close();
    }

    @Test
    public void f() {
        // 一级缓存
        IUserDao mapper = sqlSession.getMapper(IUserDao.class);
        IUserDao mapper1 = sqlSession.getMapper(IUserDao.class);
        List<User> all = mapper.findAll();
        List<User> all1 = mapper1.findAll();
        for (User user : all) {
            System.out.println(user);
        }
        System.out.println("---------------------------");
        for (User user : all1) {
            System.out.println(user);
        }
    }

    @Test
    public void f1() {
        IUserDao mapper = sqlSession.getMapper(IUserDao.class);
        IUserDao mapper1 = sqlSession.getMapper(IUserDao.class);
        List<User> all = mapper.findAll();
        List<User> all1 = mapper1.findAll();
        // 同一对象
        System.out.println(all == all1);
        for (User user : all) {
            System.out.println(user);
        }
        // 出现脏读
        all.remove(1);
        System.out.println("---------------------------");
        for (User user : all1) {
            System.out.println(user);
        }
    }

    @Test
    public void f2() {
        IUserDao mapper = sqlSession.getMapper(IUserDao.class);
        List<User> all = mapper.findAll();
        for (User user : all) {
            System.out.println(user);
        }
        // 保存二级缓存的内容
        sqlSession.close();
        System.out.println("---------------------------");

        sqlSession = sqlSessionFactory.openSession();
        IUserDao mapper1 = sqlSession.getMapper(IUserDao.class);
        List<User> all1 = mapper1.findAll();
        for (User user : all1) {
            System.out.println(user);
        }
    }
}
