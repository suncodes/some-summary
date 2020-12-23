package suncodes.jdbc.tradition;

import java.sql.*;

/**
 * 使用原生的JDBC进行操作数据库
 * 操作步骤：
 * 1、加载驱动
 * 2、创建连接
 * 3、获取要执行的sql句柄，通过连接对象获取对SQL语句的执行者对象
 * 4、执行sql
 * 5、获取结果
 * 6、关闭句柄及连接
 */
public class TraditionJDBC {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // 1、加载驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        // 2. 获取连接
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://192.168.6.111:3306/jdbc",
                "jdbc",
                "jdbc"
        );
        // sql执行对象
        PreparedStatement preparedStatement = connection.prepareStatement("select * from my_resource");
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
