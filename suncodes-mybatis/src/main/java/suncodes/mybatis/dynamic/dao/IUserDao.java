package suncodes.mybatis.dynamic.dao;

import org.apache.ibatis.annotations.Param;
import suncodes.mybatis.dynamic.domain.User;

import java.util.List;
import java.util.Map;

public interface IUserDao {
    /**
     * 查询所有操作
     * @return 用户列表
     */
    List<User> findAll();

    List<User> selectByUser(User user);

    List<User> selectByUser2(User user);

    List<User> selectByIds(List<Integer> uIds);

    List<User> selectByIdAndName(@Param("a") Map<Integer, String> idAndNameMap);
}
