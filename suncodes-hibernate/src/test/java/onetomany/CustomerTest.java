package onetomany;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import suncodes.onetomany.dao.Customer;
import suncodes.onetomany.dao.Order;

public class CustomerTest {
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    @Before
    public void before() {
        ServiceRegistry serviceRegistry =
                new StandardServiceRegistryBuilder().configure("onetoamny/hibernate.cfg.xml").build();

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
    public void testMany2OneSave(){
        Customer customer = new Customer();
        customer.setCustomerName("AA");

        Order order1 = new Order();
        order1.setOrderName("ORDER-1");

        Order order2 = new Order();
        order2.setOrderName("ORDER-2");

        //设定关联关系
        order1.setCustomer(customer);
        order2.setCustomer(customer);

        customer.getOrders().add(order1);
        customer.getOrders().add(order2);

        //执行  save 操作: 先插入 Customer, 再插入 Order, 3 条 INSERT, 2 条 UPDATE
        //因为 1 的一端和 n 的一端都维护关联关系. 所以会多出 UPDATE
        // 说白了就是两方会同时维护关联关系，导致重复了
        session.save(customer);

		session.save(order1);
		session.save(order2);
    }

    @Test
    public void testMany2OneSave2(){
        Customer customer = new Customer();
        customer.setCustomerName("AA");

        Order order1 = new Order();
        order1.setOrderName("ORDER-1");

        Order order2 = new Order();
        order2.setOrderName("ORDER-2");

        //设定关联关系
        order1.setCustomer(customer);
        order2.setCustomer(customer);

        customer.getOrders().add(order1);
        customer.getOrders().add(order2);

        // 先插入 Order, 再插入 Cusomer, 3 条 INSERT, 4 条 UPDATE
        // 先插入两条 order，之后插入 customer，之后更新 order表，之后更新customer
		session.save(order1);
		session.save(order2);

		session.save(customer);
    }

    @Test
    public void testOne2ManySave() {
        Customer customer = new Customer();
        customer.setCustomerName("AA");

        Order order1 = new Order();
        order1.setOrderName("ORDER-1");

        Order order2 = new Order();
        order2.setOrderName("ORDER-2");

        //设定关联关系
        order1.setCustomer(customer);
        order2.setCustomer(customer);

        customer.getOrders().add(order1);
        customer.getOrders().add(order2);

        //执行  save 操作: 先插入 Customer, 再插入 Order, 3 条 INSERT, 2 条 UPDATE
        //因为 1 的一端和 n 的一端都维护关联关系. 所以会多出 UPDATE
        //可以在 1 的一端的 set 节点指定 inverse=true, 来使 1 的一端放弃维护关联关系!
        //建议设定 set 的 inverse=true, 建议先插入 1 的一端, 后插入多的一端
        //好处是不会多出 UPDATE 语句
        // 配合 <set> 标签的inverse属性，可以让 1 的一方放弃维护关联关系
        // 配合 cascade="save-update" 可以级联保存
        session.save(customer);
    }

    @Test
    public void testMany2OneGet(){
        // 懒加载
        //1. 若查询多的一端的一个对象, 则默认情况下, 只查询了多的一端的对象. 而没有查询关联的 1 的那一端的对象!
        Order order = session.get(Order.class, 1);
        System.out.println(order.getOrderName());

        System.out.println(order.getCustomer().getClass().getName());

        session.close();

        //2. 在需要使用到关联的对象时, 才发送对应的 SQL 语句.
        Customer customer = order.getCustomer();
        System.out.println(customer.getCustomerName());

        //3. 在查询 Customer 对象时, 由多的一端导航到 1 的一端时,
        //若此时 session 已被关闭, 则默认情况下
        //会发生 LazyInitializationException 异常

        //4. 获取 Order 对象时, 默认情况下, 其关联的 Customer 对象是一个代理对象!

    }

    @Test
    public void testOne2ManyGet(){
        //1. 对 n 的一端的集合使用延迟加载
        Customer customer = session.get(Customer.class, 7);
        System.out.println(customer.getCustomerName());
        //2. 返回的多的一端的集合时 Hibernate 内置的集合类型.
        //该类型具有延迟加载和存放代理对象的功能.
        System.out.println(customer.getOrders().getClass());

        //session.close();
        //3. 可能会抛出 LazyInitializationException 异常

        System.out.println(customer.getOrders().size());

        //4. 再需要使用集合中元素的时候进行初始化.
    }

    /**
     * 配合 <one-to-one> 中的 cascade属性（级联删除），可以做到删除 1 的一端，级联删除多的一端。
     */
    @Test
    public void testCascade(){
        Customer customer = session.get(Customer.class, 3);
        customer.getOrders().clear();
    }

    @Test
    public void testDelete(){
        //在不设定级联关系的情况下, 且 1 这一端的对象有 n 的对象在引用, 不能直接删除 1 这一端的对象
        Customer customer = session.get(Customer.class, 1);
        session.delete(customer);
    }

    @Test
    public void testUpdat2(){
        Customer customer = session.get(Customer.class, 1);
        customer.getOrders().iterator().next().setOrderName("GGG");
    }

    @Test
    public void testUpdate(){
        Order order = session.get(Order.class, 1);
        order.getCustomer().setCustomerName("AAA");
    }
}
