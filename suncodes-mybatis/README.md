## mybatis:
有关 mybatis 相关的代码，对应笔记 03-数据库技术 > 03-Mybatis

对应 Spring 版本 5.2.7-RELEASE；MySQL 版本 8.0.18；Mybatis 版本 3.5.6

### MyBatis 概述

mybatis的概述
	mybatis是一个持久层框架，用java编写的。
	它封装了jdbc操作的很多细节，使开发者只需要关注sql语句本身，而无需关注注册驱动，创建连接等繁杂过程
	它使用了ORM思想实现了结果集的封装。

	ORM：
		Object Relational Mappging 对象关系映射
		简单的说：
			就是把数据库表和实体类及实体类的属性对应起来
			让我们可以操作实体类就实现操作数据库表。

			user			User
			id			userId
			user_name		userName
	今天我们需要做到
		实体类中的属性和数据库表的字段名称保持一致。
			user			User
			id			id
			user_name		user_name

### Mybatis 框架快速入门
#### Mybatis 框架开发的准备
（1）MySQL 下载 （略）

（2）MySQL数据 导入

```sql

DROP DATABASE IF EXISTS mybatis;

CREATE DATABASE IF NOT EXISTS mybatis charset utf8;

USE mybatis;

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL auto_increment,
  `username` varchar(32) NOT NULL COMMENT '用户名称',
  `birthday` datetime default NULL COMMENT '生日',
  `sex` char(1) default NULL COMMENT '性别',
  `address` varchar(256) default NULL COMMENT '地址',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


insert  into `user`(`id`,`username`,`birthday`,`sex`,`address`) values (41,'老王','2018-02-27 17:47:08','男','北京'),(42,'小二王','2018-03-02 15:09:37','女','北京金燕龙'),(43,'小二王','2018-03-04 11:34:34','女','北京金燕龙'),(45,'传智播客','2018-03-04 12:04:06','男','北京金燕龙'),(46,'老王','2018-03-07 17:37:26','男','北京'),(48,'小马宝莉','2018-03-08 11:44:00','女','北京修正');


DROP TABLE IF EXISTS `account`;

CREATE TABLE `account` (
  `ID` int(11) NOT NULL COMMENT '编号',
  `UID` int(11) default NULL COMMENT '用户编号',
  `MONEY` double default NULL COMMENT '金额',
  PRIMARY KEY  (`ID`),
  KEY `FK_Reference_8` (`UID`),
  CONSTRAINT `FK_Reference_8` FOREIGN KEY (`UID`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


insert  into `account`(`ID`,`UID`,`MONEY`) values (1,41,1000),(2,45,1000),(3,41,2000);


DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `ID` int(11) NOT NULL COMMENT '编号',
  `ROLE_NAME` varchar(30) default NULL COMMENT '角色名称',
  `ROLE_DESC` varchar(60) default NULL COMMENT '角色描述',
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


insert  into `role`(`ID`,`ROLE_NAME`,`ROLE_DESC`) values (1,'院长','管理整个学院'),(2,'总裁','管理整个公司'),(3,'校长','管理整个学校');


DROP TABLE IF EXISTS `user_role`;

CREATE TABLE `user_role` (
  `UID` int(11) NOT NULL COMMENT '用户编号',
  `RID` int(11) NOT NULL COMMENT '角色编号',
  PRIMARY KEY  (`UID`,`RID`),
  KEY `FK_Reference_10` (`RID`),
  CONSTRAINT `FK_Reference_10` FOREIGN KEY (`RID`) REFERENCES `role` (`ID`),
  CONSTRAINT `FK_Reference_9` FOREIGN KEY (`UID`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert  into `user_role`(`UID`,`RID`) values (41,1),(45,1),(41,2);

```
### 搭建 Mybatis 开发环境
#### 引入相关依赖
此过程不依赖 Spring 
```xml

<properties>
    <mybatis.version>3.5.6</mybatis.version>
    <mysql.version>8.0.18</mysql.version>
    <spring.version>5.2.7.RELEASE</spring.version>
</properties>

<dependencies>
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>${mysql.version}</version>
    </dependency>
    <!-- mybatis 坐标 -->
    <dependency>
        <groupId>org.mybatis</groupId>
        <artifactId>mybatis</artifactId>
        <version>${mybatis.version}</version>
    </dependency>
    <!-- 日志相关依赖 start -->
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-api</artifactId>
        <version>1.7.30</version>
    </dependency>
    <dependency>
        <groupId>ch.qos.logback</groupId>
        <artifactId>logback-classic</artifactId>
        <version>1.2.3</version>
    </dependency>
    <dependency>
        <groupId>ch.qos.logback</groupId>
        <artifactId>logback-core</artifactId>
        <version>1.2.3</version>
    </dependency>
    <!-- 日志相关依赖 end -->
</dependencies>

```

#### 创建实体类
```java
package suncodes.mybatis.xml.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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

#### 持久层接口
```java
package suncodes.mybatis.xml.dao;

import suncodes.mybatis.xml.domain.User;

import java.util.List;

public interface IUserDao {
    /**
     * 查询所有操作
     * @return 用户列表
     */
    List<User> findAll();
}
```

#### 持久层接口对应配置文件
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="suncodes.mybatis.xml.dao.IUserDao">
    <!--配置查询所有-->
    <select id="findAll" resultType="suncodes.mybatis.xml.domain.User">
        select * from user
    </select>
</mapper>
```

目录：resources/suncodes/mybatis/xml/dao/IUserDao.xml

#### SqlMapConfig.xml配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <!-- 配置环境，默认激活的是id为mysql的配置 -->
    <environments default="mysql">
        <!-- 配置mysql环境 -->
        <environment id="mysql">
            <transactionManager type="JDBC" />
            <!-- 配置数据源（连接池） -->
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                <!-- 需要设置时区 -->
                <property name="url" value="jdbc:mysql://localhost:3306/mybatis?serverTimezone=UTC"/>
                <property name="username" value="root"/>
                <property name="password" value="root"/>
            </dataSource>
        </environment>
    </environments>

    <!-- 指定映射配置文件的位置，映射配置文件指的是每个dao独立的配置文件 -->
    <mappers>
        <mapper resource="suncodes/mybatis/xml/dao/IUserDao.xml" />
    </mappers>
</configuration>
```

#### 测试类
```java
@Test
public void xml() throws IOException {
    // 1、读取配置文件
    InputStream resourceAsStream = Resources.getResourceAsStream("mybatis/SqlMapConfig.xml");
    // 2、创建 SqlSessionFactory 工厂
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
    // 3、创建 SqlSession 对象
    SqlSession sqlSession = sqlSessionFactory.openSession();
    // 4、创建代理对象
    IUserDao userDao = sqlSession.getMapper(IUserDao.class);
    // 5、执行方法
    List<User> userList = userDao.findAll();
    for (User user : userList) {
        System.out.println(user);
    }
    // 6、释放资源
    sqlSession.close();
    resourceAsStream.close();
}
```

#### 小结
    mybatis的环境搭建
        第一步：创建maven工程并导入坐标
        第二步：创建实体类和dao的接口
        第三步：创建Mybatis的主配置文件
                SqlMapConifg.xml
        第四步：创建映射配置文件
                IUserDao.xml
    环境搭建的注意事项：
        第一个：创建IUserDao.xml 和 IUserDao.java时名称是为了和我们之前的知识保持一致。
            在Mybatis中它把持久层的操作接口名称和映射文件也叫做：Mapper
            所以：IUserDao 和 IUserMapper是一样的
        第二个：在idea中创建目录的时候，它和包是不一样的
            包在创建时：com.itheima.dao它是三级结构
            目录在创建时：com.itheima.dao是一级目录
        第三个：mybatis的映射配置文件位置必须和dao接口的包结构相同
        第四个：映射配置文件的mapper标签namespace属性的取值必须是dao接口的全限定类名
        第五个：映射配置文件的操作配置（select），id属性的取值必须是dao接口的方法名
    
        当我们遵从了第三，四，五点之后，我们在开发中就无须再写dao的实现类。
    mybatis的入门案例
        第一步：读取配置文件
        第二步：创建SqlSessionFactory工厂
        第三步：创建SqlSession
        第四步：创建Dao接口的代理对象
        第五步：执行dao中的方法
        第六步：释放资源
    
        注意事项：
            不要忘记在映射配置中告知mybatis要封装到哪个实体类中
            配置的方式：指定实体类的全限定类名

### mybatis 注解方式

（1）创建实体类

略

（2）创建 DAO

```java
public interface IUserDao {
    /**
     * 查询所有操作
     * @return 用户列表
     */
    @Select("select * from user limit 1")
    List<User> findAll();
}
```

（3）配置文件

mybatis/SqlMapConfigAnnotation.xml

```xml
    <mappers>
        <!-- 任意一种 -->
        <package name="suncodes.mybatis.annotation.dao"/>
<!--        <mapper class="suncodes.mybatis.annotation.dao.IUserDao" />-->
    </mappers>
```

注：如果 xml 和注解方式同时存在，则 xml 优先

### mybatis 实现接口实现类方式

（1）实体类

（2）DAO 接口

（3）实现类

```java
public class UserDaoImpl implements IUserDao {

    private SqlSessionFactory sqlSessionFactory;

    public UserDaoImpl(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    public List<User> findAll() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<User> users = sqlSession.selectList("suncodes.mybatis.impl.dao.IUserDao.findAll");
        sqlSession.close();
        return users;
    }
}
```

（4）配置文件

（5）测试

```java
    @Test
    public void xml() throws IOException {
        // 1、读取配置文件
        InputStream resourceAsStream = Resources.getResourceAsStream("mybatis/SqlMapConfigImpl.xml");
        // 2、创建 SqlSessionFactory 工厂
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        // 3、创建 SqlSession 对象
//        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 4、创建代理对象
        IUserDao userDao = new UserDaoImpl(sqlSessionFactory);
        // 5、执行方法
        List<User> userList = userDao.findAll();
        for (User user : userList) {
            System.out.println(user);
        }
        // 6、释放资源
//        sqlSession.close();
        resourceAsStream.close();
    }
```

