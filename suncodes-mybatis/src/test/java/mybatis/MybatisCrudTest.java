package mybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import suncodes.mybatis.crud.dao.IUserDao;
import suncodes.mybatis.crud.domain.User;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class MybatisCrudTest {

    private InputStream resourceAsStream;

    private SqlSession sqlSession;

    @Before
    public void init() throws IOException {
        // 1、读取配置文件
        resourceAsStream = Resources.getResourceAsStream("mybatis/SqlMapConfigCrud.xml");
        // 2、创建 SqlSessionFactory 工厂
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
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
    public void xml() throws IOException {
        // 4、创建代理对象
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        // 5、执行方法
        List<User> userList = userDao.findAll();
        for (User user : userList) {
            System.out.println(user);
        }
    }

    @Test
    public void insert() throws IOException {
        // 4、创建代理对象
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        // 5、执行方法
        User user = new User();
        user.setUsername("sunchuizhe");
        user.setSex("男");
        user.setAddress("zhengzhou");
        user.setBirthday(new Date(LocalDate.of(1995, 11, 3).toEpochDay()));
        userDao.saveUser(user);
        System.out.println(user);
    }

    @Test
    public void update() throws IOException {
        // 4、创建代理对象
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        // 5、执行方法
        User user = new User();
        user.setId(50);
        user.setUsername("sunchuizhe");
        user.setSex("男");
        user.setAddress("henan");
        user.setBirthday(new Date(LocalDate.of(1995, 11, 3).toEpochDay()));
        userDao.updateUser(user);
    }

    @Test
    public void delete() throws IOException {
        // 4、创建代理对象
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        // 5、执行方法
        userDao.deleteUser(50);
    }

    @Test
    public void select() throws IOException {
        // 4、创建代理对象
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        // 5、执行方法
        User user = userDao.selectById(50);
        System.out.println(user);
    }

    @Test
    public void selectLike() throws IOException {
        // 4、创建代理对象
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        // 5、执行方法
        List<User> users = userDao.selectByName("%王%");
        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test
    public void selectLike2() throws IOException {
        // 4、创建代理对象
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        // 5、执行方法
        List<User> users = userDao.selectByName2("王");
        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test
    public void countUser() throws IOException {
        // 4、创建代理对象
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        // 5、执行方法
        int count = userDao.countUser();
        System.out.println(count);
    }

    @Test
    public void insertAndGetId() throws IOException {
        // 4、创建代理对象
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        // 5、执行方法
        User user = new User();
        user.setUsername("sunchuizhe");
        user.setSex("男");
        user.setAddress("zhengzhou");
        user.setBirthday(new Date());
        System.out.println(user);
        userDao.insertAndGetId(user);
        System.out.println(user);
    }
}
