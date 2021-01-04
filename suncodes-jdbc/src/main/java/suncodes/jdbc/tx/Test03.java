package suncodes.jdbc.tx;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 1. 通过getConnection获取数据库连接
 * 2. 通过setAutoCommit false 设置非自动提交
 * 3. 之后操作 connection对象进行插入数据
 * 4. 不再使用jdbcTemplate.update()方法
 * 5. 最后手动提交
 * 6. 可以保证事务
 *
 */
public class Test03 {
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
        // 但此处 更新 的时候，使用的connection
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, objects[0].toString());
        preparedStatement.setString(2, objects[1].toString());
        preparedStatement.setString(3, objects[2].toString());
        preparedStatement.executeUpdate();

        int i = 1 / 0;

        Object[] objects1 = new Object[3];
        objects1[0] = "纯情九九 露奶自摸 定制";
        objects1[1] = "video";
        objects1[2] = "https://youtu.be/aCS6PRdrMdc";
        PreparedStatement preparedStatement1 = connection.prepareStatement(sql);
        preparedStatement1.setString(1, objects1[0].toString());
        preparedStatement1.setString(2, objects1[1].toString());
        preparedStatement1.setString(3, objects1[2].toString());
        preparedStatement1.executeUpdate();
        connection.commit();
    }


    public static void main(String[] args) throws SQLException {
        insert();
    }
}
