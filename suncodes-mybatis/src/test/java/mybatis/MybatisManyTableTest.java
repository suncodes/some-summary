package mybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import suncodes.mybatis.manytable.dao.IAccountDao;
import suncodes.mybatis.manytable.dao.IUserDao;
import suncodes.mybatis.manytable.domain.AccountUser;
import suncodes.mybatis.manytable.domain.UserAccount;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MybatisManyTableTest {
    private InputStream resourceAsStream;

    private SqlSession sqlSession;

    @Before
    public void init() throws IOException {
        // 1、读取配置文件
        resourceAsStream = Resources.getResourceAsStream("mybatis/SqlMapConfigManyTable.xml");
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
    public void userFindAll() {
        IUserDao mapper = sqlSession.getMapper(IUserDao.class);
        System.out.println(mapper.findAll());
    }

    @Test
    public void accountFindAll() {
        IAccountDao mapper = sqlSession.getMapper(IAccountDao.class);
        System.out.println(mapper.findAll());
    }

    @Test
    public void accountUser() {
        IAccountDao mapper = sqlSession.getMapper(IAccountDao.class);
        List<AccountUser> accountUsers = mapper.selectAccountUser();
        for (AccountUser accountUser : accountUsers) {
            System.out.println(accountUser);
        }
    }

    @Test
    public void userAccount() {
        IUserDao mapper = sqlSession.getMapper(IUserDao.class);
        List<UserAccount> userAccounts = mapper.selectUserAccount();
        for (UserAccount userAccount : userAccounts) {
            System.out.println(userAccount);
        }
    }
}
