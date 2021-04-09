package suncodes.mybatis;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.Test;
import suncodes.mybatis.xml.dao.IUserDao;
import suncodes.mybatis.xml.domain.User;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class MybatisTest {

    /**
     * 使用 XML 方式进行配置
     * @throws IOException
     */
    @Test
    public void xml() throws IOException {
        // 1、读取配置文件
        InputStream resourceAsStream = Resources.getResourceAsStream("mybatis/SqlMapConfig.xml");
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

    /**
     * 使用 JAVA 方式进行代替 XML 配置
     * 和上面功能一样
     * @see MybatisTest#xml()
     * @throws Exception
     */
    @Test
    public void replaceXml() throws Exception {
        Properties properties = new Properties();
        properties.setProperty("driver", "com.mysql.cj.jdbc.Driver");
        properties.setProperty("url", "jdbc:mysql://localhost:3306/mybatis?serverTimezone=UTC");
        properties.setProperty("username", "root");
        properties.setProperty("password", "root");
        DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);

        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.addMapper(IUserDao.class);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);

        SqlSession sqlSession = sqlSessionFactory.openSession();
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        List<User> userList = userDao.findAll();
        for (User user : userList) {
            System.out.println(user);
        }

        sqlSession.close();
    }
}
