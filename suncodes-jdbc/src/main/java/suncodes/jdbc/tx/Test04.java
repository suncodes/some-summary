package suncodes.jdbc.tx;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@Component
public class Test04 {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public void insert() {
        String sql = "insert into my_resource(resource_name,type,address) values(?,?,?)";
        Object[] objects = new Object[3];
        objects[0] = "纯情九九 露奶 定制";
        objects[1] = "video";
        objects[2] = "https://youtu.be/7aOHTPKUw5w";
        // 因为没有事务，所以此处能够插入成功
        jdbcTemplate.update(sql, objects);

        int i = 1 / 0;

        Object[] objects1 = new Object[3];
        objects1[0] = "纯情九九 露奶自摸 定制";
        objects1[1] = "video";
        objects1[2] = "https://youtu.be/aCS6PRdrMdc";
        jdbcTemplate.update(sql, objects1);
    }
}
