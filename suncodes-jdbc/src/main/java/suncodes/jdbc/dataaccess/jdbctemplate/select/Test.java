package suncodes.jdbc.dataaccess.jdbctemplate.select;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import suncodes.jdbc.dataaccess.jdbctemplate.po.MyResource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 根据官网上改写的例子
 */
public class Test {

    private static JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        jdbcTemplate = jdbcTemplate();
        selectReturnBeaList();
    }


    public static void selectCount() {
        // 展示如何获取一个表中的所有行数。
        // TODO 过时
//        int rowCount = jdbcTemplate.queryForInt("select count(0) from t_accrual");
        String sql = "select count(0) from my_resource";
        Integer count = jdbcTemplate.queryForObject(sql, null, Integer.class);
        System.out.println(count);
    }

    public static void selectCountByParam() {
        // 一个简单的例子展示如何进行参数绑定。
        String sql = "select count(0) from my_resource where type=?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{"java"}, Integer.class);
        System.out.println(count);
    }

    public static void selectReturnString() {
        // 查询一个String。
        String sql = "select resource_name from my_resource where type=?";
//        String resourceName = jdbcTemplate.queryForObject(sql, new Object[]{"java"}, String.class);
        List<String> list = jdbcTemplate.queryForList(sql, new Object[]{"java"}, String.class);
        System.out.println(list);
    }

    public static void selectReturnBean() {
        // 查询并将结果记录为一个简单的数据模型。
        String sql = "select * from my_resource where id=?";
        MyResource myResource = jdbcTemplate.queryForObject(sql, new Object[]{1}, new RowMapper<MyResource>() {
            @Override
            public MyResource mapRow(ResultSet resultSet, int i) throws SQLException {
                MyResource myResource = new MyResource();
                myResource.setId(resultSet.getInt("id"));
                myResource.setResourceName(resultSet.getString("resource_name"));
                myResource.setType(resultSet.getString("type"));
                myResource.setAddress(resultSet.getString("address"));
                return myResource;
            }
        });
        System.out.println(myResource);
    }

    public static void selectReturnBeaList() {
        // 查询并将结果记录为一个简单的数据模型。
        String sql = "select * from my_resource";
        List<MyResource> myResource = jdbcTemplate.query(sql, new RowMapper<MyResource>() {
            @Override
            public MyResource mapRow(ResultSet resultSet, int i) throws SQLException {
                MyResource myResource = new MyResource();
                myResource.setId(resultSet.getInt("id"));
                myResource.setResourceName(resultSet.getString("resource_name"));
                myResource.setType(resultSet.getString("type"));
                myResource.setAddress(resultSet.getString("address"));
                return myResource;
            }
        });
        for (MyResource resource : myResource) {
            System.out.println(resource);
        }
    }

    private static JdbcTemplate jdbcTemplate() {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("jdbctemplate.xml");
        DruidDataSource druidDataSource = context.getBean("druidDataSource", DruidDataSource.class);

        return new JdbcTemplate(druidDataSource);
    }
}
