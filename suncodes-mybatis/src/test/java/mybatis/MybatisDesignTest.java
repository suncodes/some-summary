package mybatis;

import org.junit.Test;
import suncodes.mybatis.design.dao.IUserAnnotationDao;
import suncodes.mybatis.design.dao.IUserDao;
import suncodes.mybatis.design.domain.User;
import suncodes.mybatis.design.io.Resources;
import suncodes.mybatis.design.sqlsession.SqlSession;
import suncodes.mybatis.design.sqlsession.SqlSessionFactory;
import suncodes.mybatis.design.sqlsession.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 自定义mybatis
 */
public class MybatisDesignTest {

    @Test
    public void xml() throws IOException {
        // 1、读取配置文件
        InputStream resourceAsStream = Resources.getResourceAsStream("mybatis/SqlMapConfigDesign.xml");
        // 2、创建 SqlSessionFactory 工厂
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        // 3、创建 SqlSession 对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 4、创建代理对象
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        // 5、执行方法
        List<User> userList = userDao.findAll();
        for (User user : userList) {
            System.out.println(user);
        }
        // 6、释放资源
        sqlSession.close();
        resourceAsStream.close();
    }

    @Test
    public void annotation() throws IOException {
        // 1、读取配置文件
        InputStream resourceAsStream = Resources.getResourceAsStream("mybatis/SqlMapConfigDesign.xml");
        // 2、创建 SqlSessionFactory 工厂
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        // 3、创建 SqlSession 对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 4、创建代理对象
        IUserAnnotationDao userDao = sqlSession.getMapper(IUserAnnotationDao.class);
        // 5、执行方法
        List<User> userList = userDao.findAll();
        for (User user : userList) {
            System.out.println(user);
        }
        // 6、释放资源
        sqlSession.close();
        resourceAsStream.close();
    }
}
