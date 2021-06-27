# pom 文件

```xml
    <dependencies>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>${mysql.version}</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
            <version>${springboot.version}</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <version>${springboot.version}</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <version>${springboot.version}</version>
            <scope>test</scope>
        </dependency>
    </dependencies>
```

# 配置实体类

```java
@Entity(name = "BOOK")
public class Book {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
}
```

# 配置hibernate

```yml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/hibernate?serverTimezone=UTC
    username: root
    password: root
  jpa:
    hibernate:
      ddl-auto: update
    database-platform: org.hibernate.dialect.MySQL8Dialect

```

# 配置 DAO

```java
package suncodes.hibernate.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import suncodes.hibernate.pojo.po.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
```

# 测试

```java
@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> list() {
        return bookRepository.findAll();
    }

    public void insert() {
        Book book = new Book("The Tartar Steppe");
        Book book1 = new Book("Poem Strip");
        Book book2 = new Book("Restless Nights: Selected Stories of Dino Buzzati");
        bookRepository.save(book);
        bookRepository.save(book1);
        bookRepository.save(book2);
    }
}
```

```java
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
```