package onecache;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.jdbc.Work;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import suncodes.onecache.dao.News;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

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

    /**
     * 1. save() 方法
     * 1). 使一个临时对象变为持久化对象
     * 2). 在 flush 缓存时会发送一条 INSERT 语句.
     * 3). 在 save 方法之前的 id 是无效的，但是不会报错，会自动按照自增的主键给对象重新分配一个id
     * 5). 持久化对象的 ID 是不能被修改的!，会直接报错。
     */
    @Test
    public void testSave() {
        News news = new News();
        // 无效，会自动分配id
        news.setId(4);
        news.setTitle("testSave");
        news.setAuthor("scz");
        news.setDate(new Date());
        session.save(news);
        // 此处程序报错
        news.setId(10);
    }

    /**
     * persist(): 也会执行 INSERT 操作
     *
     * 和 save() 的区别 :
     * 在调用 persist 方法之前, 若对象已经有 id 了, 则不会执行 INSERT, 而抛出异常
     */
    @Test
    public void testPersist(){
        News news = new News();
        news.setTitle("EE");
        news.setAuthor("ee");
        news.setDate(new Date());
        news.setId(200);
        // 直接报错
        session.persist(news);
    }

    /**
     * 1、功能：查询数据库，获取数据
     * 2、当查询的数据库数据不存在的时候，返回 null
     * 3、get 方法立即加载，不支持延迟加载策略
     *
     */
    @Test
    public void testGet(){
        News news = session.get(News.class, 11);
        // 测试是否支持延迟加载，看此句话和打印的 SQL 语句的先后顺序
        System.out.println("-------------------");
        System.out.println(news);
    }

    /**
     * 1、功能：查询数据库，获取数据
     * 2、当查询的数据库数据不存在的时候，抛出异常
     * 3、load 方法支持延迟加载策略
     *
     */
    @Test
    public void testLoad(){

        News news = session.load(News.class, 10);
        // suncodes.onecache.dao.News$HibernateProxy$iYLcNTo6
        System.out.println(news.getClass().getName());
        // 测试是否支持延迟加载，看此句话和打印的 SQL 语句的先后顺序
        System.out.println("-------------------------");
        // 真正执行查询
		System.out.println(news);
    }

    /**
     * update:
     * 1. 若更新一个持久化对象, 不需要显示的调用 update 方法. 因为在调用 Transaction
     * 的 commit() 方法时, 会先执行 session 的 flush 方法.
     * 2. 更新一个游离对象, 需要显式的调用 session 的 update 方法. 可以把一个游离对象
     * 变为持久化对象
     *
     * 需要注意的:
     * 1. 无论要更新的游离对象和数据表的记录是否一致, 都会发送 UPDATE 语句.
     *    如何能让 update 方法不再盲目的出发 update 语句呢 ? 在 .hbm.xml 文件的 class 节点设置
     *    select-before-update=true (默认为 false). 但通常不需要设置该属性.
     *
     * 2. 若数据表中没有对应的记录, 但还调用了 update 方法, 会抛出异常
     *
     * 3. 当 update() 方法关联一个游离对象时,
     * 如果在 Session 的缓存中已经存在相同 OID 的持久化对象, 会抛出异常. 因为在 Session 缓存中
     * 不能有两个 OID 相同的对象!
     *
     */
    @Test
    public void testUpdate(){
        News news = session.get(News.class, 1);
        // 目前：创建一个游离状态的对象
        transaction.commit();
        session.close();

//		news.setId(100);

        // 重新开启一个 Session
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();

//		news.setAuthor("SUN");

        // 查询，把 news2 加入 Session 缓存
        News news2 = session.get(News.class, 1);
        // 执行更新操作，检查更新前缓存中是否有和 news 对象 一样的 oid，如果有，则报错
        session.update(news);
    }

    /**
     * 注意:
     * 1. 若 OID 不为 null, 但数据表中还没有和其对应的记录. 会抛出一个异常.
     * 2. 了解: OID 值等于 id 的 unsaved-value 属性值的对象, 也被认为是一个游离对象
     */
    @Test
    public void testSaveOrUpdate(){
        News news = new News("FFF", "fff", new Date());
        news.setId(11);

        session.saveOrUpdate(news);
    }

    /**
     * delete: 执行删除操作. 只要 OID 和数据表中一条记录对应, 就会准备执行 delete 操作
     * 若 OID 在数据表中没有对应的记录, 则抛出异常
     *
     * 可以通过设置 hibernate 配置文件 hibernate.use_identifier_rollback 为 true,
     * 使删除对象后, 把其 OID 置为  null
     */
    @Test
    public void testDelete(){
//		News news = new News();
//		news.setId(11);

        News news = session.get(News.class, 163840);
        session.delete(news);

        System.out.println(news);
    }

    /**
     * evict: 从 session 缓存中把指定的持久化对象移除
     */
    @Test
    public void testEvict(){
        News news1 = session.get(News.class, 1);
        News news2 = session.get(News.class, 2);

        news1.setTitle("AA");
        news2.setTitle("BB");

        session.evict(news1);
    }

    /**
     * 调用 过程
     * 1、创建过程
     * CREATE DEFINER=`root`@`localhost` PROCEDURE `testProcedure`()
     * BEGIN
     * 	select * from news;
     * 	insert into news(title,author,`date`) values('HHH','HHH',NOW());
     * END
     *
     * 2、hibernate 没有直接操作过程的API，所以需要通过原生的JDBC
     * Work 接口: 直接通过 JDBC API 来访问数据库的操作
     */
    @Test
    public void testDoWork(){
        session.doWork(new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                System.out.println(connection);
                //调用存储过程.
                String procedure = "{call testProcedure()}";
                CallableStatement statement = connection.prepareCall(procedure);
                statement.executeUpdate();
            }
        });
    }
}
