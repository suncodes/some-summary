package mapping;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import suncodes.mapping.dao.News;

import java.io.*;
import java.sql.Blob;
import java.util.Date;

public class NewsTest {
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    @Before
    public void before() {
        ServiceRegistry serviceRegistry =
                new StandardServiceRegistryBuilder().configure("mapping/hibernate.cfg.xml").build();

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
     *     insert
     *     into
     *         NEWS
     *         (AUTHOR, ID)
     *     values
     *         (?, ?)
     */
    @Test
    public void testDynamicUpdate(){
        News news = new News();
        news.setAuthor("dynamic-insert");
        session.save(news);
    }

    @Test
    public void testPropertyUpdate(){
        News news = session.get(News.class, 1);
        news.setTitle("aaaa");

        System.out.println(news.getDesc());
        System.out.println(news.getDate().getClass());
    }

    @Test
    public void testBlob() throws Exception{
		News news = new News();
		news.setAuthor("cc");
		news.setContent("CONTENT");
		news.setDate(new Date());
		news.setDesc("DESC");
		news.setTitle("CC");

		InputStream stream = NewsTest.class.getClassLoader().getResourceAsStream("hehe.jpg");
		Blob image = Hibernate.getLobCreator(session).createBlob(stream, stream.available());
		news.setPicture(image);

		session.save(news);
    }

    @Test
    public void testBlob2() throws Exception{
        News news = session.get(News.class, 5);
        Blob image = news.getPicture();

        InputStream in = image.getBinaryStream();
        System.out.println(in.available());
    }
}
