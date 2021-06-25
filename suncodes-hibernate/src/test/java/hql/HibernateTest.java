package hql;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
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

    /**
     * hql 查询
     * 注意 hibernate5 的hql和之前的语法好像不一样，？后面需要加上数字才能正常运行
     * 记住一点：hql是面向对象的，而不是面向表字段的。
     */
    @Test
    public void testHelloWord() {
        String hql = "FROM Employee e WHERE e.salary >= ?1 AND e.email LIKE ?2";
        Query query = session.createQuery(hql);

        //2. 绑定参数
        //Query 对象调用 setXxx 方法支持方法链的编程风格.
        query.setFloat(1, 6000)
                .setString(2, "%a%");

        //3. 执行查询
        List<Employee> emps = query.list();
        System.out.println(emps.size());
    }

    /**
     * 参数绑定：命名参数绑定
     * 格式：冒号 + 命名参数
     */
    @Test
    public void testHQLNamedParameter() {

        //1. 创建 Query 对象
        //基于命名参数.
        String hql = "FROM Employee e WHERE e.salary > :sal AND e.email LIKE :email";
        Query query = session.createQuery(hql);

        //2. 绑定参数
        query.setFloat("sal", 7000)
                .setString("email", "%%");

        //3. 执行查询
        List<Employee> emps = query.list();
        System.out.println(emps.size());
    }

    /**
     * setEntity(): 把参数与一个持久化类绑定，只需要赋值主键就可以了
     * order by
     * 明显看到 有关 setXXX 方法过期了需要使用 #setParameter(int, Object, Type) 代替
     */
    @Test
    public void testHQL() {

        //1. 创建 Query 对象
        //基于位置的参数.
        String hql = "FROM Employee e WHERE e.salary > ?3 AND e.email LIKE ?1 AND e.dept = ?2 "
                + "ORDER BY e.salary";
        Query query = session.createQuery(hql);

        //2. 绑定参数
        //Query 对象调用 setXxx 方法支持方法链的编程风格.
        Department dept = new Department();
        dept.setId(1);
        query.setFloat(3, 5000)
                .setString(1, "%a%")
                .setEntity(2, dept);

        //3. 执行查询
        List<Employee> emps = query.list();
        System.out.println(emps.size());
    }

    /**
     * 使用 setParameter 代替其他 setXXX
     */
    @Test
    public void testSetParameter() {

        //1. 创建 Query 对象
        //基于位置的参数.
        String hql = "FROM Employee e WHERE e.salary > ?3 AND e.email LIKE ?1 AND e.dept = ?2 "
                + "ORDER BY e.salary";
        Query query = session.createQuery(hql);

        //2. 绑定参数
        //Query 对象调用 setXxx 方法支持方法链的编程风格.
        Department dept = new Department();
        dept.setId(1);
        query.setParameter(3, 5000, IntegerType.INSTANCE)
                .setParameter(1, "%a%", StringType.INSTANCE)
                .setParameter(2, dept);

        //3. 执行查询
        List<Employee> emps = query.list();
        System.out.println(emps.size());
    }

    /**
     * 分页查询
     */
    @Test
    public void testPageQuery() {
        String hql = "FROM Employee";
        Query query = session.createQuery(hql);

        int pageNo = 1;
        int pageSize = 3;

        List<Employee> emps =
                query.setFirstResult((pageNo - 1) * pageSize)
                        .setMaxResults(pageSize)
                        .list();
        System.out.println(emps);
    }

    /**
     * 在映射文件中定义命名查询语句
     */
    @Test
    public void testNamedQuery() {
        Query query = session.getNamedQuery("salaryEmps");

        List<Employee> emps = query.setFloat("minSal", 5000)
                .setFloat("maxSal", 10000)
                .list();

        System.out.println(emps.size());
    }

    /**
     * 投影查询
     */
    @Test
    public void testFieldQuery() {
        String hql = "SELECT e.email, e.salary, e.dept FROM Employee e WHERE e.dept = :dept";
        Query query = session.createQuery(hql);

        Department dept = new Department();
        dept.setId(1);
        List<Object[]> result = query.setEntity("dept", dept)
                .list();

        for (Object[] objs : result) {
            System.out.println(Arrays.asList(objs));
        }
    }

    /**
     * 投影查询：通过select new Employee 方式返回部分字段
     */
    @Test
    public void testFieldQuery2() {
        String hql = "SELECT new Employee(e.email, e.salary, e.dept) "
                + "FROM Employee e "
                + "WHERE e.dept = :dept";
        Query query = session.createQuery(hql);

        Department dept = new Department();
        dept.setId(1);
        List<Employee> result = query.setEntity("dept", dept)
                .list();

        for (Employee emp : result) {
            System.out.println(emp.getId() + ", " + emp.getEmail()
                    + ", " + emp.getSalary() + ", " + emp.getDept());
        }
    }

    /**
     * 投影查询 + 去重：
     * 如果要返回所有字段，则 select 可以省略；
     * 如果需要去重，则可以根据 SELECT distinct 别名
     */
    @Test
    public void testFieldQuery3() {
        String hql = "SELECT distinct e "
                + "FROM Employee e "
                + "WHERE e.dept = :dept";
        Query query = session.createQuery(hql);

        Department dept = new Department();
        dept.setId(1);
        List<Employee> result = query.setEntity("dept", dept)
                .list();

        for (Employee emp : result) {
            System.out.println(emp.getId() + ", " + emp.getEmail()
                    + ", " + emp.getSalary() + ", " + emp.getDept());
        }
    }

    /**
     * 聚合函数
     */
    @Test
    public void testGroupBy() {
        String hql = "SELECT min(e.salary), max(e.salary) "
                + "FROM Employee e "
                + "GROUP BY e.dept "
                + "HAVING min(salary) > :minSal";

        Query query = session.createQuery(hql)
                .setFloat("minSal", 5000);

        List<Object[]> result = query.list();
        for (Object[] objs : result) {
            System.out.println(Arrays.asList(objs));
        }
    }

    @Test
    public void testLeftJoinFetch2() {
        String hql = "SELECT e FROM Employee e INNER JOIN e.dept";
        Query query = session.createQuery(hql);

        List<Employee> emps = query.list();
        System.out.println(emps.size());

        for (Employee emp : emps) {
            System.out.println(emp.getName() + ", " + emp.getDept().getName());
        }
    }

    @Test
    public void testLeftJoin() {
        String hql = "SELECT DISTINCT d FROM Department d LEFT JOIN d.emps";
        Query query = session.createQuery(hql);

        List<Department> depts = query.list();
        System.out.println(depts.size());

        for (Department dept : depts) {
            System.out.println(dept.getName() + ", " + dept.getEmps().size());
        }
    }

    /**
     * 推荐使用 LEFT JOIN FETCH
     */
    @Test
    public void testLeftJoinFetch() {
//		String hql = "SELECT DISTINCT d FROM Department d LEFT JOIN FETCH d.emps";
        String hql = "FROM Department d INNER JOIN FETCH d.emps";
        Query query = session.createQuery(hql);

        List<Department> depts = query.list();
        depts = new ArrayList<>(new LinkedHashSet(depts));
        System.out.println(depts.size());

        for (Department dept : depts) {
            System.out.println(dept.getName() + "-" + dept.getEmps().size());
        }
    }


    /**
     * QBC 查询就是通过使用 Hibernate 提供的 Query By Criteria API 来查询对象
     */
    @Test
    public void testQBC() {
        //1. 创建一个 Criteria 对象
        Criteria criteria = session.createCriteria(Employee.class);

        //2. 添加查询条件: 在 QBC 中查询条件使用 Criterion 来表示
        //Criterion 可以通过 Restrictions 的静态方法得到
        criteria.add(Restrictions.eq("email", "SKUMAR"));
        criteria.add(Restrictions.gt("salary", 5000F));

        //3. 执行查询
        Employee employee = (Employee) criteria.uniqueResult();
        System.out.println(employee);
    }

    @Test
    public void testQBC2() {
        Criteria criteria = session.createCriteria(Employee.class);

        //1. AND: 使用 Conjunction 表示
        //Conjunction 本身就是一个 Criterion 对象
        //且其中还可以添加 Criterion 对象
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.like("name", "a", MatchMode.ANYWHERE));
        Department dept = new Department();
        dept.setId(80);
        conjunction.add(Restrictions.eq("dept", dept));
        System.out.println(conjunction);

        //2. OR
        Disjunction disjunction = Restrictions.disjunction();
        disjunction.add(Restrictions.ge("salary", 6000F));
        disjunction.add(Restrictions.isNull("email"));

        criteria.add(disjunction);
        criteria.add(conjunction);

        criteria.list();
    }

    @Test
    public void testQBC3() {
        Criteria criteria = session.createCriteria(Employee.class);

        //统计查询: 使用 Projection 来表示: 可以由 Projections 的静态方法得到
        criteria.setProjection(Projections.max("salary"));

        System.out.println(criteria.uniqueResult());
    }

    @Test
    public void testQBC4() {
        Criteria criteria = session.createCriteria(Employee.class);

        //1. 添加排序
        criteria.addOrder(Order.asc("salary"));
        criteria.addOrder(Order.desc("email"));

        //2. 添加翻页方法
        int pageSize = 5;
        int pageNo = 3;
        criteria.setFirstResult((pageNo - 1) * pageSize)
                .setMaxResults(pageSize)
                .list();
    }

    @Test
    public void testNativeSQL() {
        String sql = "INSERT INTO gg_department VALUES(?, ?)";
        Query query = session.createSQLQuery(sql);

        query.setInteger(1, 3)
                .setString(2, "ATGUIGU")
                .executeUpdate();
    }

    @Test
    public void testHQLUpdate() {
        String hql = "DELETE FROM Department d WHERE d.id = :id";

        session.createQuery(hql).setInteger("id", 280)
                .executeUpdate();
    }
}
