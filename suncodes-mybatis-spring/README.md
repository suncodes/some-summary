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
        <property name="sqlSessionFactory" ref="sqlSessionFactory"/>
    </bean>
```

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


# 4、mybatis:scan


# 5、@MapperScan


# 6、MapperScannerConfigurer


