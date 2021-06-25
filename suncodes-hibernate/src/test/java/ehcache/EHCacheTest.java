package ehcache;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.jdbc.Work;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import suncodes.ehcache.dao.Department;
import suncodes.ehcache.dao.Employee;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EHCacheTest {
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    @Before
    public void before() {
        ServiceRegistry serviceRegistry =
                new StandardServiceRegistryBuilder().configure("ehcache/hibernate.cfg.xml").build();

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
    public void insertData() {
        Employee employee = new Employee();
        employee.setName("aa");
        employee.setSalary(6000);
        employee.setEmail("aa@qq.com");

        Employee employee1 = new Employee();
        employee1.setName("bb");
        employee1.setSalary(6100);
        employee1.setEmail("bb@qq.com");

        Employee employee2 = new Employee();
        employee2.setName("cc");
        employee2.setSalary(5000);
        employee2.setEmail("cc@qq.com");

        Employee employee3 = new Employee();
        employee3.setName("dd");
        employee3.setSalary(10000);
        employee3.setEmail("dd@qq.com");

        Department department = new Department();
        department.setName("技术部");

        Department department2 = new Department();
        department2.setName("市场部");

        employee.setDept(department);
        employee1.setDept(department2);
        employee2.setDept(department);
        employee3.setDept(department2);

        Set<Employee> set = new HashSet<>();
        set.add(employee);
        set.add(employee2);
        department.setEmps(set);

        Set<Employee> set1 = new HashSet<>();
        set1.add(employee1);
        set1.add(employee3);
        department2.setEmps(set1);

        session.save(employee);
        session.save(employee1);
        session.save(employee2);
        session.save(employee3);
        session.save(department);
        session.save(department2);
    }

    @Test
    public void testHibernateSecondLevelCache(){
        Employee employee = session.get(Employee.class, 1);
        System.out.println(employee.getName());

        transaction.commit();
        session.close();

        session = sessionFactory.openSession();
        transaction = session.beginTransaction();

        Employee employee2 = session.get(Employee.class, 1);
        System.out.println(employee2.getName());
    }

    @Test
    public void testCollectionSecondLevelCache(){
        Department dept = session.get(Department.class, 1);
        System.out.println(dept.getName());
        System.out.println(dept.getEmps().size());

        transaction.commit();
        session.close();

        session = sessionFactory.openSession();
        transaction = session.beginTransaction();

        Department dept2 = session.get(Department.class, 1);
        System.out.println(dept2.getName());
        System.out.println(dept2.getEmps().size());
    }

    /**
     * HQL 使用二级缓存
     */
    @Test
    public void testQueryCache(){
        Query query = session.createQuery("FROM Employee");
        query.setCacheable(true);

        List<Employee> emps = query.list();
        System.out.println(emps.size());

        emps = query.list();
        System.out.println(emps.size());

        Criteria criteria = session.createCriteria(Employee.class);
        criteria.setCacheable(true);
    }

    /**
     * 批量处理
     * HQL 不支持
     */
    @Test
    public void testBatch(){
        session.doWork(new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                //通过 JDBC 原生的 API 进行操作, 效率最高, 速度最快!
            }
        });
    }
}
