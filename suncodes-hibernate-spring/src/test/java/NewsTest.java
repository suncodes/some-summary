import dao.NewsDao;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pojo.po.News;

public class NewsTest {
    @Test
    public void f() {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("application.xml");
        NewsDao newsDao = context.getBean("newsDao", NewsDao.class);

        News news = newsDao.getById(1);
        System.out.println(news);
    }
}
