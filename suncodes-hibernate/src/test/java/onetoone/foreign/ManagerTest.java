package onetoone.foreign;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import suncodes.onetoone.foreign.dao.Department;
import suncodes.onetoone.foreign.dao.Manager;

public class ManagerTest {
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    @Before
    public void before() {
        ServiceRegistry serviceRegistry =
                new StandardServiceRegistryBuilder().configure("onetoone/foreign/hibernate.cfg.xml").build();

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
    public void testGet2(){
        // 在查询没有外键的实体对象时, 使用的左外连接查询, 一并查询出其关联的对象
        // 并已经进行初始化.
        Manager mgr = session.get(Manager.class, 1);
        System.out.println(mgr.getMgrName());
        System.out.println(mgr.getDept().getDeptName());
    }

    @Test
    public void testGet(){
        // 1. 默认情况下对关联属性使用懒加载
        Department dept = session.get(Department.class, 1);
        System.out.println(dept.getDeptName());

        // 2. 所以会出现懒加载异常的问题.
//		session.close();
//		Manager mgr = dept.getMgr();
//		System.out.println(mgr.getClass());
//		System.out.println(mgr.getMgrName());

        // 3. 查询 Manager 对象的连接条件应该是 dept.manager_id = mgr.manager_id
        // 而不应该是 dept.dept_id = mgr.manager_id
        Manager mgr = dept.getMgr();
        System.out.println(mgr.getMgrName());
    }

    @Test
    public void testSave(){

        Department department = new Department();
        department.setDeptName("DEPT-BB");

        Manager manager = new Manager();
        manager.setMgrName("MGR-BB");

        // 设定关联关系
        department.setMgr(manager);
        manager.setDept(department);

        // 保存操作
        // 建议先保存没有外键列的那个对象. 这样会减少 UPDATE 语句
        session.save(department);
        session.save(manager);
    }
}
