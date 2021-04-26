package mybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import suncodes.mybatis.dynamic.dao.IUserDao;
import suncodes.mybatis.dynamic.domain.User;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MybatisDynamicTest {

    private InputStream resourceAsStream;

    private SqlSession sqlSession;

    @Before
    public void init() throws IOException {
        // 1、读取配置文件
        resourceAsStream = Resources.getResourceAsStream("mybatis/SqlMapConfigDynamic.xml");
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
    public void f() {
        User user = new User();
        user.setUsername("老王");
        IUserDao mapper = sqlSession.getMapper(IUserDao.class);
        List<User> users = mapper.selectByUser(user);
        for (User u : users) {
            System.out.println(u);
        }
    }

    @Test
    public void f2() {
        User user = new User();
        user.setUsername("老王");
        IUserDao mapper = sqlSession.getMapper(IUserDao.class);
        List<User> users = mapper.selectByUser2(user);
        for (User u : users) {
            System.out.println(u);
        }
    }

    @Test
    public void f3() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(50);
        list.add(51);
        list.add(52);
        IUserDao mapper = sqlSession.getMapper(IUserDao.class);
        List<User> users = mapper.selectByIds(list);
        for (User u : users) {
            System.out.println(u);
        }
    }

    @Test
    public void f4() {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "");
        map.put(50, "");
        map.put(51, "");
        map.put(52, "");
        IUserDao mapper = sqlSession.getMapper(IUserDao.class);
        List<User> users = mapper.selectByIdAndName(map);
        for (User u : users) {
            System.out.println(u);
        }
    }
}
