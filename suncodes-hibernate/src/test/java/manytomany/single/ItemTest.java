package manytomany.single;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import suncodes.manytomany.single.dao.Category;
import suncodes.manytomany.single.dao.Item;

import java.util.Set;

public class ItemTest {
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    @Before
    public void before() {
        ServiceRegistry serviceRegistry =
                new StandardServiceRegistryBuilder().configure("manytomany/single/hibernate.cfg.xml").build();

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
    public void testGet(){
        Category category = session.get(Category.class, 1);
        System.out.println(category.getName());

        //需要连接中间表
        Set<Item> items = category.getItems();
        System.out.println(items.size());
    }

    @Test
    public void testSave(){
        Category category1 = new Category();
        category1.setName("C-AA");

        Category category2 = new Category();
        category2.setName("C-BB");

        Item item1 = new Item();
        item1.setName("I-AA");

        Item item2 = new Item();
        item2.setName("I-BB");

        //设定关联关系
        category1.getItems().add(item1);
        category1.getItems().add(item2);

        category2.getItems().add(item1);
        category2.getItems().add(item2);

        //执行保存操作
        session.save(category1);
        session.save(category2);

        session.save(item1);
        session.save(item2);
    }
}
