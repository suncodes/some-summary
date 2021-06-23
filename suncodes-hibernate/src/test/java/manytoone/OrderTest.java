package manytoone;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import suncodes.manytoone.dao.Customer;
import suncodes.manytoone.dao.Order;

public class OrderTest {
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    @Before
    public void before() {
        ServiceRegistry serviceRegistry =
                new StandardServiceRegistryBuilder().configure("manytoone/hibernate.cfg.xml").build();

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
    public void testDelete(){
        // 在不设定级联关系的情况下, 且 1 这一端的对象有 n 的对象在引用, 不能直接删除 1 这一端的对象
        Customer customer = session.get(Customer.class, 1);
        session.delete(customer);
    }

    @Test
    public void testUpdate(){
        Order order = session.get(Order.class, 1);
        order.getCustomer().setCustomerName("AAA");
    }

    @Test
    public void testMany2OneGet(){
        //1. 若查询多的一端的一个对象, 则默认情况下, 只查询了多的一端的对象. 而没有查询关联的 1 的那一端的对象!
        Order order = session.get(Order.class, 1);
        System.out.println(order.getOrderName());

        System.out.println(order.getCustomer().getClass().getName());

        // 不能关闭 session，因为对于多对一来说，是延迟加载查询
        // 如果关闭 session，则会报异常
//        session.close();

        //2. 在需要使用到关联的对象时, 才发送对应的 SQL 语句.
        Customer customer = order.getCustomer();
        System.out.println(customer.getCustomerName());

        //3. 在查询 Customer 对象时, 由多的一端导航到 1 的一端时,
        //若此时 session 已被关闭, 则默认情况下
        //会发生 LazyInitializationException 异常

        //4. 获取 Order 对象时, 默认情况下, 其关联的 Customer 对象是一个代理对象!

    }

    /**
     * update
     *         ORDERS
     *     set
     *         ORDER_NAME=?,
     *         CUSTOMER_ID=?
     *     where
     *         ORDER_ID=?
     */
    @Test
    public void testMany2OneSave(){
        Customer customer = new Customer();
        customer.setCustomerName("BB");

        Order order1 = new Order();
        order1.setOrderName("ORDER-3");

        Order order2 = new Order();
        order2.setOrderName("ORDER-4");

        //设定关联关系
        order1.setCustomer(customer);
        order2.setCustomer(customer);

        //先插入 Order, 再插入 Customer. 3 条 INSERT, 2 条 UPDATE
        //先插入 n 的一端, 再插入 1 的一端, 会多出 UPDATE 语句!
        //因为在插入多的一端时, 无法确定 1 的一端的外键值. 所以只能等 1 的一端插入后, 再额外发送 UPDATE 语句.
        //推荐先插入 1 的一端, 后插入 n 的一端
        session.save(order1);
        session.save(order2);

        session.save(customer);
    }

    /**
     *     create table CUSTOMERS (
     *        CUSTOMER_ID integer not null auto_increment,
     *         CUSTOMER_NAME varchar(255),
     *         primary key (CUSTOMER_ID)
     *     ) engine=InnoDB
     *
     *     create table ORDERS (
     *        ORDER_ID integer not null auto_increment,
     *         ORDER_NAME varchar(255),
     *         CUSTOMER_ID integer,
     *         primary key (ORDER_ID)
     *     ) engine=InnoDB
     *
     *     alter table ORDERS
     *        add constraint FKflgddyesjyik2ro2p501yys8r
     *        foreign key (CUSTOMER_ID)
     *        references CUSTOMERS (CUSTOMER_ID)
     */
    @Test
    public void testMany2OneSave1(){
        Customer customer = new Customer();
        customer.setCustomerName("AA");

        Order order1 = new Order();
        order1.setOrderName("ORDER-1");

        Order order2 = new Order();
        order2.setOrderName("ORDER-2");

        //设定关联关系
        order1.setCustomer(customer);
        order2.setCustomer(customer);

        //执行  save 操作: 先插入 Customer, 再插入 Order, 3 条 INSERT
        //先插入 1 的一端, 再插入 n 的一端, 只有 INSERT 语句.
		session.save(customer);

		session.save(order1);
		session.save(order2);
    }
}
