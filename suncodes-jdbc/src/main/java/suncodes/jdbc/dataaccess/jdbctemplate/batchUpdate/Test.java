package suncodes.jdbc.dataaccess.jdbctemplate.batchUpdate;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        JdbcTemplate jdbcTemplate = jdbcTemplate();
        String sql = "insert into my_resource(resource_name,type,address) values(?,?,?)";
        List<Object[]> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Object[] objects = new Object[3];
            objects[0] = "ceshi";
            objects[0] = "ceshi";
            objects[0] = "ceshi";
            list.add(objects);
        }
        jdbcTemplate.batchUpdate(sql, list);
    }

    private static JdbcTemplate jdbcTemplate() {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("jdbctemplate.xml");
        DruidDataSource druidDataSource = context.getBean("druidDataSource", DruidDataSource.class);

        return new JdbcTemplate(druidDataSource);
    }
}
