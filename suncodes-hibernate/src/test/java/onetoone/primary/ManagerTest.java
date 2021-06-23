package onetoone.primary;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import suncodes.onetoone.primary.dao.Department;
import suncodes.onetoone.primary.dao.Manager;


public class ManagerTest {
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    @Before
    public void before() {
        ServiceRegistry serviceRegistry =
                new StandardServiceRegistryBuilder().configure("onetoone/primary/hibernate.cfg.xml").build();

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
        //在查询没有外键的实体对象时, 使用的左外连接查询, 一并查询出其关联的对象
        //并已经进行初始化.
        Manager mgr = session.get(Manager.class, 1);
        System.out.println(mgr.getMgrName());
        System.out.println(mgr.getDept().getDeptName());
    }

    @Test
    public void testGet(){
        //1. 默认情况下对关联属性使用懒加载
        Department dept = session.get(Department.class, 1);
        System.out.println(dept.getDeptName());

        //2. 所以会出现懒加载异常的问题.
        Manager mgr = dept.getMgr();
        System.out.println(mgr.getMgrName());
    }

    @Test
    public void testSave(){

        Department department = new Department();
        department.setDeptName("DEPT-DD");

        Manager manager = new Manager();
        manager.setMgrName("MGR-DD");

        //设定关联关系
        manager.setDept(department);
        department.setMgr(manager);

        // 保存操作
        // 先插入哪一个都不会有多余的 UPDATE
        // 因为 dept 的主键依赖于 manager
        session.save(department);
        session.save(manager);
    }
}
