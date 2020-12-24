package suncodes.jdbc.tradition;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class TraditionJDBCProperties {
    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
        // 1、加载驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        // 2. 获取连接
        Properties properties = new Properties();
        properties.load(TraditionJDBCProperties.class.getClassLoader().getResourceAsStream("tradition-jdbc.properties"));
        Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.6.111:3306/jdbc", properties);
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
