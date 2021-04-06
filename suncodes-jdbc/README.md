## 目录含义
- jdbc:
- dataaccess：数据访问，在学习spring官方文档的时候，一些例子。主要就是使用jdbcTemplate，SimpleJdbcTemplate等进行操作数据库。
- dbutil：apache工具类
- dbutilspring：apache工具类和spring结合使用
- jdbctemplate：jdbcTemplate的简单用法
- tradition：传统方式的jdbc
- tx：事务相关
- txconfig：java config形式的事务

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

