package suncodes.admin.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import suncodes.admin.bean.City;

//@Mapper
public interface CityMapper {

    @Select("select * from city where id=#{id}")
    public City getById(Long id);


    @Insert("insert into  city(`name`,`state`,`country`) values(#{name},#{state},#{country})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    public void insert(City city);

}
