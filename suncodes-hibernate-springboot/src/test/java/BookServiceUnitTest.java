import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import suncodes.hibernate.HibernateSpringApplication;
import suncodes.hibernate.pojo.po.Book;
import suncodes.hibernate.service.BookService;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HibernateSpringApplication.class)
public class BookServiceUnitTest {

    @Autowired
    private BookService bookService;

    @Test
    public void whenApplicationStarts_thenHibernateCreatesInitialRecords() {
        List<Book> books = bookService.list();
        System.out.println(books);
        Assert.assertEquals(books.size(), 3);
    }

    @Test
    public void f() {
        bookService.insert();
    }
}