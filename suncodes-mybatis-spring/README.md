# 1、环境准备

## 1.1、引入pom坐标

```xml
<dependency>
  <groupId>org.mybatis</groupId>
  <artifactId>mybatis-spring</artifactId>
  <version>2.0.6</version>
</dependency>

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
```

## 1.2、创建表（略）

## 1.3、创建实体类

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer id;
    private String username;
    private Date birthday;
    private String sex;
    private String address;
}
```

## 1.4、创建 Mapper 接口
```java
public interface IUserDao {
    /**
     * 查询所有操作
     * @return 用户列表
     */
    List<User> findAll();
}
```
# 2、入门

## 2.1、数据源配置
```properties
# datasource.properties
druid.driverClassName=com.mysql.cj.jdbc.Driver
druid.url=jdbc:mysql://localhost:3306/mybatis?serverTimezone=UTC
druid.username=root
druid.password=root
```

Datasource.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd">

    <!-- 加载配置文件 -->
    <context:property-placeholder location="classpath:datasource.properties"/>

    <bean id="datasource" class="com.alibaba.druid.pool.DruidDataSource">
        <property name="driverClassName" value="${druid.driverClassName}"/>
        <property name="url" value="${druid.url}"/>
        <property name="username" value="${druid.username}"/>
        <property name="password" value="${druid.password}"/>
    </bean>
</beans>
```

## 2.2、配置SqlSessionFactory
```xml
<import resource="classpath*:Datasource.xml"/>

    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="datasource"/>
        <property name="mapperLocations" value="mybatis/*.xml"/>
    </bean>
```
- mapperLocations：map xml文件所在位置

## 2.3、配置SqlSession

注：不配置也可以正常运行（MapperFactoryBean配置sqlSessionFactory，会自动创建SqlSessionTemplate）

```xml
    <bean id="sqlSession" class="org.mybatis.spring.SqlSessionTemplate">
        <constructor-arg ref="sqlSessionFactory"/>
    </bean>
```

## 2.4、配置MapperFactoryBean
```xml
    <bean id="userDao" class="org.mybatis.spring.mapper.MapperFactoryBean">
        <property name="mapperInterface" value="suncodes.mybatis.spring.dao.IUserDao"/>
        <!-- 如果使用 sqlSessionFactory，则SqlSessionTemplate会自动进行创建 -->
        <property name="sqlSessionFactory" ref="sqlSessionFactory"/>
        <!-- sqlSessionFactory 和 sqlSessionTemplate 二选一即可 -->
        <!--<property name="sqlSessionTemplate" ref="sqlSession"/>-->
    </bean>
```
- mapperInterface：映射器接口
- sqlSessionFactory：sqlSession工厂，如果通过sqlSessionFactory，则不必自己定义sqlSessionTemplate了。
- sqlSessionTemplate：sqlSession实现类

## 2.5、测试

```java
    @Test
    public void f() {
        ClassPathXmlApplicationContext context = 
                new ClassPathXmlApplicationContext("MybatisSpringMapper.xml");
        IUserDao userDao = context.getBean("userDao", IUserDao.class);
        List<User> users = userDao.findAll();
        for (User user : users) {
            System.out.println(user);
        }
    }
```

## 2.6、总结

- MyBatis中核心类：
  - SqlSessionFactory（读取Configuration配置，解析xml）；
  - SqlSession（操作SQL语句，进行增删改查）；
  - getMapper（获取动态代理类，用于执行对应的方法）

- SqlSessionFactoryBean ===== SqlSessionFactory
- SqlSessionTemplate ===== SqlSession
- MapperFactoryBean ===== 动态代理类

- 只要抓住这三个核心，工厂类，Session类，映射器类，就可以了。

- SqlSessionFactoryBean：
  - IOC管理，可以配置数据库，mapperLocations
- SqlSessionTemplate:
  - 对数据库连接进行操作，执行SQL语句等
  - 可以隐式创建
- MapperFactoryBean：
  - 动态代理类，这是获取动态代理类的一种方式
  - \<mybatis:scan/\>
  - @MapperScan
  - MapperScannerConfigurer

# 3、基于 Spring 管理的 SqlSession 手动编写 DAO

## 3.1、实体类（略）

## 3.2、映射器接口
```java
package suncodes.mybatis.spring.dao;

import suncodes.mybatis.spring.domain.User;

import java.util.List;

public interface IUserDao {
    /**
     * 查询所有操作
     * @return 用户列表
     */
    List<User> findAll();
}
```

## 3.3、映射器接口实现类
```java
package suncodes.mybatis.spring.dao.impl;

import org.apache.ibatis.session.SqlSession;
import suncodes.mybatis.spring.dao.IUserDao;
import suncodes.mybatis.spring.domain.User;

import java.util.List;

public class UserDaoImpl implements IUserDao {

    private SqlSession sqlSession;

    public void setSqlSession(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public List<User> findAll() {
        return sqlSession.selectList("suncodes.mybatis.spring.dao.IUserDao.findAll");
    }
}

```

## 3.4、配置文件
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!-- 数据源 -->
    <import resource="classpath*:Datasource.xml"/>
    <!-- 工厂类 -->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="datasource"/>
        <property name="mapperLocations" value="mybatis/*.xml"/>
    </bean>
    <!-- sql操作类 -->
    <bean id="sqlSession" class="org.mybatis.spring.SqlSessionTemplate">
        <constructor-arg index="0" ref="sqlSessionFactory"/>
    </bean>
    <!-- 代理类 -->
    <bean id="userDao" class="suncodes.mybatis.spring.dao.impl.UserDaoImpl">
        <property name="sqlSession" ref="sqlSession"/>
    </bean>
</beans>
```

## 3.5、映射器实现类2
```java
package suncodes.mybatis.spring.dao.impl;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import suncodes.mybatis.spring.dao.IUserDao;
import suncodes.mybatis.spring.domain.User;

import java.util.List;

public class UserDaoImplSupport extends SqlSessionDaoSupport implements IUserDao {
    @Override
    public List<User> findAll() {
        return getSqlSession().selectList("suncodes.mybatis.spring.dao.IUserDao.findAll");
    }
}

```
SqlSessionDaoSupport 是一个抽象的支持类，用来为你提供 SqlSession。调用 getSqlSession() 方法你会得到一个 SqlSessionTemplate，之后可以用于执行 SQL 方法

## 3.6、配置文件2
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    <!-- 数据源 -->
    <import resource="classpath*:Datasource.xml"/>
    <!-- 工厂类 -->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="datasource"/>
        <property name="mapperLocations" value="mybatis/*.xml"/>
    </bean>
    <!-- sql操作类 -->
    <bean id="sqlSession" class="org.mybatis.spring.SqlSessionTemplate">
        <constructor-arg index="0" ref="sqlSessionFactory"/>
    </bean>
    <!-- 代理类 -->
    <bean id="userDao" class="suncodes.mybatis.spring.dao.impl.UserDaoImplSupport">
        <property name="sqlSessionFactory" ref="sqlSessionFactory"/>
    </bean>
</beans>
```

# 4、mybatis:scan

## 4.1、映射器接口
```java
package suncodes.mybatis.spring.dao.mapperscan;

import suncodes.mybatis.spring.domain.User;

import java.util.List;

public interface IUserDao {
    /**
     * 查询所有操作
     * @return 用户列表
     */
    List<User> findAll();
}
```
此文件在 mapperscan 包下

## 4.2、配置文件
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:mybatis="http://mybatis.org/schema/mybatis-spring"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://mybatis.org/schema/mybatis-spring http://mybatis.org/schema/mybatis-spring.xsd">

    <import resource="classpath*:Datasource.xml"/>

    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="datasource"/>
        <property name="mapperLocations" value="mybatis/*.xml"/>
    </bean>

    <!--<bean id="sqlSession" class="org.mybatis.spring.SqlSessionTemplate">-->
        <!--<constructor-arg index="0" ref="sqlSessionFactory"/>-->
    <!--</bean>-->

    <!-- 扫描mappers接口，之后自动注入到IOC容器；注意：不需要对应的实现类，只需要SqlSessionFactory被注入 -->
    <mybatis:scan base-package="suncodes.mybatis.spring.dao.mapperscan"/>
</beans>
```

## 4.3、测试类
```java
/**
 * &lt; mybatis:scan base-package="suncodes.mybatis.spring.dao.mapperscan"/&gt;
 */
public class MybatisSpringScanXmlTest {

    @Test
    public void f() {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("MybatisSpringScan.xml");
        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }
        IUserDao userDao = context.getBean("IUserDao", IUserDao.class);
        List<User> users = userDao.findAll();
        for (User user : users) {
            System.out.println(user);
        }
    }
}

```

# 5、@MapperScan

## 5.1、映射器接口（略）

## 5.2、配置类
```java
package suncodes.mybatis.spring.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@MapperScan("suncodes.mybatis.spring.dao.mapperscan")
@Configuration
@PropertySource("classpath:datasource.properties")
public class MapperScanConfig {

    @Value("${druid.driverClassName}")
    private String driverClassName;

    @Value("${druid.url}")
    private String url;

    @Value("${druid.username}")
    private String username;

    @Value("${druid.password}")
    private String password;

    @Bean
    public DataSource datasource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(driverClassName);
        druidDataSource.setUrl(url);
        druidDataSource.setUsername(username);
        druidDataSource.setPassword(password);
        return druidDataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(datasource());
        sqlSessionFactoryBean.setMapperLocations(new ClassPathResource("mybatis/MapperScanIUserDao.xml"));
        return sqlSessionFactoryBean.getObject();
    }
}

```

注意几点：
- @Configuration：声明配置类
- @PropertySource：读取有关的properties文件
- @Value：根据读取的配置文件获取对应的属性
- 声明一个DataSource
- 声明一个SqlSessionFactory
- @MapperScan：扫描对应的mappers接口

# 6、MapperScannerConfigurer

```java
package suncodes.mybatis.spring.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@PropertySource("classpath:datasource.properties")
@Configuration
public class MapperScannerConfig {

    @Value("${druid.driverClassName}")
    private String driverClassName;

    @Value("${druid.url}")
    private String url;

    @Value("${druid.username}")
    private String username;

    @Value("${druid.password}")
    private String password;

    @Bean
    public DataSource datasource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        druidDataSource.setUrl("jdbc:mysql://localhost:3306/mybatis?serverTimezone=UTC");
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("root");
        return druidDataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(datasource());
        sqlSessionFactoryBean.setMapperLocations(new ClassPathResource("mybatis/MapperScanIUserDao.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("suncodes.mybatis.spring.dao.mapperscan");
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        return mapperScannerConfigurer;
    }
}

```
- MapperScannerConfigurer:Scan for mappers and let them be autowired; notice there is no UserDaoImplementation needed. The required SqlSessionFactory will be autowired

