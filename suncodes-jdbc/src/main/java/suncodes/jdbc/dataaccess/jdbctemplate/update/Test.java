package suncodes.jdbc.dataaccess.jdbctemplate.update;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import suncodes.jdbc.dataaccess.jdbctemplate.po.MyResource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Test {
    private static JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        jdbcTemplate = jdbcTemplate();

        jdbcTemplate.update("insert into my_resource(resource_name, type, address) values (?,?,?)",
                new Object[]{"he", "he", "he"});
        jdbcTemplate.update("update my_resource set resource_name=?where address='he'",
                new Object[]{"hehe"});
        jdbcTemplate.update("delete from my_resource where address='he'");
    }

    private static JdbcTemplate jdbcTemplate() {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("jdbctemplate.xml");
        DruidDataSource druidDataSource = context.getBean("druidDataSource", DruidDataSource.class);

        return new JdbcTemplate(druidDataSource);
    }
}
