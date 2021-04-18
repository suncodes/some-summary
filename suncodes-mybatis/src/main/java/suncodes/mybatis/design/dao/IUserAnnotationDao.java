package suncodes.mybatis.design.dao;

import suncodes.mybatis.design.annotations.Select;
import suncodes.mybatis.design.domain.User;

import java.util.List;

/**
 * 测试注解形式
 */
public interface IUserAnnotationDao {
    /**
     * 查询所有操作
     * @return 用户列表
     */
    @Select("select * from user limit 2")
    List<User> findAll();
}
