package suncodes.mybatis.handwrite.mapper;

import suncodes.mybatis.handwrite.entity.User;
import suncodes.mybatis.handwrite.orm.annotation.ExtInsert;
import suncodes.mybatis.handwrite.orm.annotation.ExtParam;
import suncodes.mybatis.handwrite.orm.annotation.ExtSelect;

public interface UserMapper {

	@ExtInsert("insert into user(userName,userAge) values(#{userName},#{userAge})")
	public int insertUser(@ExtParam("userName") String userName, @ExtParam("userAge") Integer userAge);

	@ExtSelect("select * from User where userName=#{userName} and userAge=#{userAge} ")
	User selectUser(@ExtParam("userName") String name, @ExtParam("userAge") Integer userAge);

}
