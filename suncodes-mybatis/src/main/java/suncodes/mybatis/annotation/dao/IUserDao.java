package suncodes.mybatis.annotation.dao;

import org.apache.ibatis.annotations.Select;
import suncodes.mybatis.annotation.domain.User;

import java.util.List;

public interface IUserDao {
    /**
     * 查询所有操作
     * @return 用户列表
     */
    @Select("select * from user limit 1")
    List<User> findAll();
}
