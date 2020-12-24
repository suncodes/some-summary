package suncodes.jdbc.tradition;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

public class TraditionJDBCDruid {
    public static void main(String[] args) throws Exception {
        // 2. 获取连接
        Properties properties = new Properties();
        properties.load(TraditionJDBCDruid.class.getClassLoader().getResourceAsStream("druid.properties"));
        DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);

        Connection connection = dataSource.getConnection();

        String sql = "select * from my_resource where id = ?";
        // sql执行对象
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, 2);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String resourceName = resultSet.getString("resource_name");
            String type = resultSet.getString("type");
            String address = resultSet.getString("address");
            System.out.println("" +
                    "id: " + id + "\n" +
                    "resource_name: " + resourceName + "\n" +
                    "type: " + type + "\n" +
                    "address: " + address);
        }
        preparedStatement.close();
        connection.close();
    }
}
