# 引入pom.xml

## mysql 相关
```xml
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>${mysql.version}</version>
        </dependency>
```

## hibernate相关
```xml
        <dependency>
            <groupId>org.hibernate</groupId>
            <artifactId>hibernate-core</artifactId>
            <version>${hibernate.version}</version>
        </dependency>

        <!-- https://mvnrepository.com/artifact/com.mchange/c3p0 -->
        <dependency>
            <groupId>com.mchange</groupId>
            <artifactId>c3p0</artifactId>
            <version>0.9.5.5</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/c3p0/c3p0 -->
        <dependency>
            <groupId>c3p0</groupId>
            <artifactId>c3p0</artifactId>
            <version>0.9.1.2</version>
        </dependency>
```

## spring 相关
```xml
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>${spring.version}</version>
        </dependency>

        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-jdbc</artifactId>
            <version>${spring.version}</version>
        </dependency>
        
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-orm</artifactId>
            <version>${spring.version}</version>
        </dependency>
```

## 日志
```xml
        <!-- 日志相关依赖 start -->
        <!-- https://mvnrepository.com/artifact/org.slf4j/slf4j-api -->
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-api</artifactId>
            <version>1.7.30</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/ch.qos.logback/logback-classic -->
        <dependency>
            <groupId>ch.qos.logback</groupId>
            <artifactId>logback-classic</artifactId>
            <version>1.2.3</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/ch.qos.logback/logback-core -->
        <dependency>
            <groupId>ch.qos.logback</groupId>
            <artifactId>logback-core</artifactId>
            <version>1.2.3</version>
        </dependency>
        <!-- 日志相关依赖 end -->
```


# 新建实体类
```java
public class News {
    private Integer id;
    private String title;
    private String author;
    private Date date;
}
```

# 映射 XML 文件
```xml
<hibernate-mapping package="pojo.po">

    <class name="News" table="NEWS">
        <id name="id" column="ID">
            <generator class="native"/>
        </id>
        <property name="title" type="java.lang.String" column="TITLE" />
        <property name="author" type="java.lang.String" column="AUTHOR"/>
        <property name="date" type="java.util.Date" column="DATE"/>
    </class>

</hibernate-mapping>
```

# DAO 操作类
```java
public class NewsDao {

    private HibernateTemplate template;

    public void setTemplate(HibernateTemplate template) {
        this.template = template;
    }

    public void saveNews(News e) {
        template.save(e);
    }

    public void updateNews(News e) {
        template.update(e);
    }

    public void deleteNews(News e) {
        template.delete(e);
    }

    public News getById(int id) {
        News e = template.get(News.class, id);
        return e;
    }

    public List<News> getEmployees() {
        List<News> list = template.loadAll(News.class);
        return list;
    }
}
```

还有另外一种写法：就是继承 HibernateDaoSupport 

```java
@Transactional
public class EmployeeDao extends HibernateDaoSupport {

    /**
     * method to save employee
     */
    public void saveEmployee(Employee e) {
        getHibernateTemplate().save(e);
    }

    /**
     * method to update employee
     */
    public void updateEmployee(Employee e) {
        getHibernateTemplate().update(e);
    }

    /**
     * method to delete employee
     */
    public void deleteEmployee(Employee e) {
        getHibernateTemplate().delete(e);
    }

    /**
     * method to return one employee of given id
     */
    public Employee getById(int id) {
        Employee e = getHibernateTemplate().get(Employee.class, id);
        return e;
    }

    /**
     * method to return all employees
     */
    public List<Employee> getEmployees() {
        List<Employee> list = new ArrayList<Employee>();
        list = getHibernateTemplate().loadAll(Employee.class);
        return list;
    }
}
```

同样在声明 bean 的时候，配置hibernateTemplate 或 sessionFactory

```xml
    <bean id="employeeDao" class="dao.EmployeeDao">
        <property name="hibernateTemplate" ref="template"/>
    <!--<property name="sessionFactory" ref="mySessionFactory"/>-->
    </bean>
```

# 配置类
application.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
        <property name="driverClass" value="com.mysql.cj.jdbc.Driver"/>
        <property name="jdbcUrl" value="jdbc:mysql://localhost:3306/hibernate?serverTimezone=UTC"/>
        <property name="user" value="root"/>
        <property name="password" value="root"/>
    </bean>

    <bean id="mySessionFactory" class="org.springframework.orm.hibernate5.LocalSessionFactoryBean">
        <property name="dataSource" ref="dataSource"/>
        <property name="mappingResources">
            <list>
                <value>hbm/News.hbm.xml</value>
            </list>
        </property>

        <property name="hibernateProperties">
            <props>
                <prop key="hibernate.dialect">org.hibernate.dialect.MySQL8Dialect</prop>
                <prop key="hibernate.dialect.storage_engine">innodb</prop>
                <prop key="hibernate.hbm2ddl.auto">update</prop>
                <prop key="hibernate.show_sql">true</prop>
            </props>
        </property>
    </bean>

    <bean id="template" class="org.springframework.orm.hibernate5.HibernateTemplate">
        <property name="sessionFactory" ref="mySessionFactory"/>
    </bean>

    <bean id="newsDao" class="dao.NewsDao">
        <property name="template" ref="template"/>
    </bean>
</beans>
```

# 测试类
```java
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
```

# HibernateTemplate

| No.  | Method                                          | Description                                                  |
| :--- | :---------------------------------------------- | :----------------------------------------------------------- |
| 1)   | void persist(Object entity)                     | persists the given object.                                   |
| 2)   | Serializable save(Object entity)                | persists the given object and returns id.                    |
| 3)   | void saveOrUpdate(Object entity)                | persists or updates the given object. If id is found, it updates the record otherwise saves the record. |
| 4)   | void update(Object entity)                      | updates the given object.                                    |
| 5)   | void delete(Object entity)                      | deletes the given object on the basis of id.                 |
| 6)   | Object get(Class entityClass, Serializable id)  | returns the persistent object on the basis of given id.      |
| 7)   | Object load(Class entityClass, Serializable id) | returns the persistent object on the basis of given id.      |
| 8)   | List loadAll(Class entityClass)                 | returns the all the persistent objects.                      |