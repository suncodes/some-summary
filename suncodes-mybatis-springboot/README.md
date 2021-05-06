# 1、pom文件
```xml
<properties>
        <java.version>1.8</java.version>
        <springboot.version>2.4.5</springboot.version>
        <mysql.version>8.0.18</mysql.version>
        <mybatis-springboot.version>2.1.4</mybatis-springboot.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>${mysql.version}</version>
        </dependency>

        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>${mybatis-springboot.version}</version>
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

基本上有三个依赖：
- web：springboot启动类需要的
- mybatis-spring-boot-starter：mybatis自动注入依赖相关，包括SpringBoot-jdbc，autoconfigure，mybatis，mybatis-spring
- mysql-connector-java：mysql相关

# 2、SqlSessionFactory配置
两种方式：
- Mybatis-Config.xml配置
  - 通过config-location加载对应的配置文件
```
mybatis:
  config-location: classpath:SqlMapConfig.xml
```

- yml配置文件形式配置
  - 直接在yml配置
```
mybatis:
  type-aliases-package: com.example.demomybatis.entity
  mapper-locations: classpath:dao/*Mapper.xml
```

# 3、Mapper配置
两种方式：

- 通过@Mapper注解
> 注解说明：在dao层的类需要加上 @Mapper的注解，这个注解是mybatis提供的，标识这个类是一个数据访问层的bean，并交给spring容器管理。并且可以省去之前的xml映射文件。在编译的时候，添加了这个类也会相应的生成这个类的实现类。
- 通过@MapperScan注解

# 4、测试
```java
@SpringBootTest
class SuncodesMybatisSpringbootApplicationTests {

    @Autowired
    private IUserDao userDao;

    @Test
    void contextLoads() {
        List<User> users = userDao.findAll();
        for (User user : users) {
            System.out.println(user);
        }
    }
}
```
