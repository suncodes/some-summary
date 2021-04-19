package suncodes.mybatis.crud.dao;

import suncodes.mybatis.crud.domain.User;

import java.util.List;

public interface IUserDao {
    /**
     * 查询所有操作
     * @return 用户列表
     */
    List<User> findAll();

    void saveUser(User user);

    void updateUser(User user);

    void deleteUser(Integer id);

    User selectById(Integer id);

    List<User> selectByName(String name);

    List<User> selectByName2(String name);

    int countUser();

    void insertAndGetId(User user);
}
