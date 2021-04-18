package mybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import suncodes.mybatis.impl.dao.IUserDao;
import suncodes.mybatis.impl.dao.impl.UserDaoImpl;
import suncodes.mybatis.impl.domain.User;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MybatisImplTest {

    @Test
    public void xml() throws IOException {
        // 1、读取配置文件
        InputStream resourceAsStream = Resources.getResourceAsStream("mybatis/SqlMapConfigImpl.xml");
        // 2、创建 SqlSessionFactory 工厂
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        // 3、创建 SqlSession 对象
//        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 4、创建代理对象
        IUserDao userDao = new UserDaoImpl(sqlSessionFactory);
        // 5、执行方法
        List<User> userList = userDao.findAll();
        for (User user : userList) {
            System.out.println(user);
        }
        // 6、释放资源
//        sqlSession.close();
        resourceAsStream.close();
    }
}
