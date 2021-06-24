package hql;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import suncodes.hql.dao.Department;
import suncodes.hql.dao.Employee;

/**
 * 1、hello word
 * 2、参数绑定：命名参数方式
 * 3、setEntity(): 把参数与一个持久化类绑定
 * 4、setParameter(): 绑定任意类型的参数
 * 5、order by
 * 6、分页查询
 * 7、在映射文件中定义命名查询语句
 * 8、投影查询（注意返回值封装）
 * 9、去重
 * 10、分组查询
 * 11、连接查询
 * 12、select 关键字详解
 *
 */
public class HibernateTest {

    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    @Before
    public void before() {
        ServiceRegistry serviceRegistry =
                new StandardServiceRegistryBuilder().configure("hql/hibernate.cfg.xml").build();

        sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
        // 2. 创建一个 Session 对象
        session = sessionFactory.openSession();
        // 3. 开启事务
        transaction = session.beginTransaction();
    }

    @After
    public void after() {
        // 5. 提交事务
        transaction.commit();
        // 6. 关闭 Session
        session.close();
        // 7. 关闭 SessionFactory 对象
        sessionFactory.close();
    }

    @Test
    public void testHelloWord() {
        String hql = "FROM Employee e WHERE e.salary > ? AND e.email LIKE ?";
        Query query = session.createQuery(hql);

        //2. 绑定参数
        //Query 对象调用 setXxx 方法支持方法链的编程风格.
        query.setFloat(0, 6000)
                .setString(1, "%A%");

        //3. 执行查询
        List<Employee> emps = query.list();
        System.out.println(emps.size());
    }

    @Test
    public void testHQLNamedParameter() {

        //1. 创建 Query 对象
        //基于命名参数.
        String hql = "FROM Employee e WHERE e.salary > :sal AND e.email LIKE :email";
        Query query = session.createQuery(hql);

        //2. 绑定参数
        query.setFloat("sal", 7000)
                .setString("email", "%A%");

        //3. 执行查询
        List<Employee> emps = query.list();
        System.out.println(emps.size());
    }

    @Test
    public void testHQL() {

        //1. 创建 Query 对象
        //基于位置的参数.
        String hql = "FROM Employee e WHERE e.salary > ? AND e.email LIKE ? AND e.dept = ? "
                + "ORDER BY e.salary";
        Query query = session.createQuery(hql);

        //2. 绑定参数
        //Query 对象调用 setXxx 方法支持方法链的编程风格.
        Department dept = new Department();
        dept.setId(80);
        query.setFloat(0, 6000)
                .setString(1, "%A%")
                .setEntity(2, dept);

        //3. 执行查询
        List<Employee> emps = query.list();
        System.out.println(emps.size());
    }
}
