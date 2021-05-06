package suncodes.mybatis.springboot.dao;

import org.apache.ibatis.annotations.Mapper;
import suncodes.mybatis.springboot.domain.User;

import java.util.List;

@Mapper
public interface IUserDao {
    /**
     * 查询所有操作
     * @return 用户列表
     */
    List<User> findAll();
}
