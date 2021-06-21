package onecache;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import suncodes.onecache.dao.News;

public class NewsTest {

    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    @Before
    public void before() {
        ServiceRegistry serviceRegistry =
                new StandardServiceRegistryBuilder().configure("onecache/hibernate.cfg.xml").build();

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

    /**
     * 对于一级缓存，声明周期是基于 Session的，对同一个Session来说，前后两次查询，两次的结果是同一个对象。
     * 在 Session 接口的实现中包含一系列的 Java 集合, 这些 Java 集合构成了 Session 缓存.
     * 只要 Session 实例没有结束生命周期, 且没有清理缓存，则存放在它缓存中的对象也不会结束生命周期
     * Session 缓存可减少 Hibernate 应用程序访问数据库的频率。
     *
     * 和 mybatis 一致
     */
    @Test
    public void f() {
        News news = session.get(News.class, 1);
        System.out.println(news);
        News news2 = session.get(News.class, 1);
        // TODO 会同时写入数据库
        news2.setTitle("aaaaaa");
        System.out.println(news == news2);
        System.out.println(news);
    }

    /**
     * 对于 hibernate，通过 Session 查询出来对应的实体对象，会放入 Session缓存；
     * 之后如果修改 对应的 实体对象，之后提交对应的事务，
     * 则会把 Session 缓存（已修改的实体对象）更新到对应的数据库。
     *
     * flush: 使数据表中的记录和 Session 缓存中的对象的状态保持一致. 为了保持一致, 则可能会发送对应的 SQL 语句.
     * 1. 在 Transaction 的 commit() 方法中: 先调用 session 的 flush 方法, 再提交事务
     * 2. flush() 方法会可能会发送 SQL 语句, 但不会提交事务.
     * 3. 注意: 在未提交事务或显式的调用 session.flush() 方法之前, 也有可能会进行 flush() 操作.
     * 1). 执行 HQL 或 QBC 查询, 会先进行 flush() 操作, 以得到数据表的最新的记录
     * 2). 若记录的 ID 是由底层数据库使用自增的方式生成的, 则在调用 save() 方法时, 就会立即发送 INSERT 语句.
     * 因为 save 方法后, 必须保证对象的 ID 是存在的!
     */
    @Test
    public void testFlush() {
        News news = session.get(News.class, 1);
        // 会同时写入数据库
        news.setTitle("bbbb");
    }

    /**
     * refresh(): 会强制发送 SELECT 语句, 以使 Session 缓存中对象的状态和数据表中对应的记录保持一致!
     */
    @Test
    public void testRefresh(){
        News news = session.get(News.class, 1);
        System.out.println(news);

        session.refresh(news);
        System.out.println(news);
    }

    /**
     * clear(): 清理缓存
     */
    @Test
    public void testClear(){
        News news1 =  session.get(News.class, 1);
        session.clear();
        News news2 = session.get(News.class, 1);
        System.out.println(news1 == news2);
    }
}
