package suncodes.mybatis.springboot.dao;

import org.apache.ibatis.annotations.Mapper;
import suncodes.mybatis.springboot.domain.User;

import java.util.List;

/**
 * 两种方式：
 * 通过@Mapper注解
 * 注解说明：在dao层的类需要加上 @Mapper的注解，这个注解是mybatis提供的，
 * 标识这个类是一个数据访问层的bean，并交给spring容器管理。并且可以省去之前的xml映射文件。
 * 在编译的时候，添加了这个类也会相应的生成这个类的实现类。
 *
 * 通过@MapperScan注解
 */
@Mapper
public interface IUserDao {
    /**
     * 查询所有操作
     * @return 用户列表
     */
    List<User> findAll();
}
