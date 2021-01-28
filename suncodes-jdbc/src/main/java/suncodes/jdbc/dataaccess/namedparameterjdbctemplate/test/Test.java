package suncodes.jdbc.dataaccess.namedparameterjdbctemplate.test;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import suncodes.jdbc.dataaccess.namedparameterjdbctemplate.po.MyResource;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * namedParameterJdbcTemplate使用冒号+参数名的方式对JdbcTemplate进行了封装
 *
 * namedParameterJdbcTemplate对于参数的封装主要有两种：
 * 1、Map
 * 2、SqlParameterSource
 *
 * SqlParameterSource主要有三个是实现类
 * （1）MapSqlParameterSource
 * （2）EmptySqlParameterSource
 * （3）BeanPropertySqlParameterSource
 *
 * MapSqlParameterSource：
 * 类似map的方式，key为参数名，value为参数值
 * 可以追加多个参数
 *
 * EmptySqlParameterSource：
 * 空参数
 *
 * BeanPropertySqlParameterSource：
 * 根据一个bean的field字段名称，匹配与之相同的参数名，并对其进行赋值。
 *
 */
public class Test {

    private static NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public static void main(String[] args) {
        namedParameterJdbcTemplate = namedParameterJdbcTemplate();
        selectParamBeanPropertySqlParameterSource();
    }

    public static void selectParamMap() {
        String sql = "select count(0) from my_resource where type=:type";
        Map<String, String> map = new HashMap<>();
        map.put("type", "java");
        Integer query = namedParameterJdbcTemplate.query(sql, map, new ResultSetExtractor<Integer>() {
            @Override
            public Integer extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                int i = 0;
                while (resultSet.next()) {
                    i++;
                }
                return i;
            }
        });
        System.out.println(query);
        System.out.println("-----------------");
        Integer integer = namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);
        System.out.println(integer);
    }

    public static void selectParamMapSqlParameterSource() {
        String sql = "select count(0) from my_resource where type=:type";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("type", "java");
        Integer integer = namedParameterJdbcTemplate.queryForObject(sql, mapSqlParameterSource, Integer.class);
        System.out.println(integer);
    }

    public static void selectParamEmptySqlParameterSource() {
        String sql = "select count(0) from my_resource";
        Integer integer = namedParameterJdbcTemplate.queryForObject(sql, new EmptySqlParameterSource(), Integer.class);
        System.out.println(integer);
    }

    public static void selectParamBeanPropertySqlParameterSource() {
        String sql = "select count(0) from my_resource where type=:type";
        MyResource myResource = new MyResource();
        myResource.setType("java");
        // 其他无关参数也可以赋值，不影响，只要有sql中需要的参数即可。
        myResource.setAddress("hehe");
        BeanPropertySqlParameterSource beanPropertySqlParameterSource = new BeanPropertySqlParameterSource(myResource);
        Integer integer = namedParameterJdbcTemplate.queryForObject(sql, beanPropertySqlParameterSource, Integer.class);
        System.out.println(integer);
    }

    private static NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("jdbctemplate.xml");
        DruidDataSource druidDataSource = context.getBean("druidDataSource", DruidDataSource.class);
        return new NamedParameterJdbcTemplate(druidDataSource);
    }
}
