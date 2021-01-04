package suncodes.jdbc.tx;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 1. 通过getConnection获取数据库连接
 * 2、通过setAutoCommit false 设置非自动提交
 * 3、之后手动提交
 * 4. 因为从数据池中获取的连接不一定是一个，所以不能保证事务
 *
 */
public class Test02 {
    private static JdbcTemplate jdbcTemplate() {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("jdbctemplate.xml");
        DruidDataSource druidDataSource = context.getBean("druidDataSource", DruidDataSource.class);

        return new JdbcTemplate(druidDataSource);
    }

    private static void insert() throws SQLException {
        JdbcTemplate jdbcTemplate = jdbcTemplate();
        // 获取连接，设置非自动提交
        Connection connection = jdbcTemplate.getDataSource().getConnection();
        connection.setAutoCommit(false);

        String sql = "insert into my_resource(resource_name,type,address) values(?,?,?)";
        Object[] objects = new Object[3];
        objects[0] = "纯情九九 露奶 定制";
        objects[1] = "video";
        objects[2] = "https://youtu.be/7aOHTPKUw5w";
        // 但此处 更新 的时候，使用的connection，不一定和前面的一致
        jdbcTemplate.update(sql, objects);

        int i = 1 / 0;

        Object[] objects1 = new Object[3];
        objects1[0] = "纯情九九 露奶自摸 定制";
        objects1[1] = "video";
        objects1[2] = "https://youtu.be/aCS6PRdrMdc";
        jdbcTemplate.update(sql, objects1);
        connection.commit();
    }


    public static void main(String[] args) throws SQLException {
        insert();
    }
}
