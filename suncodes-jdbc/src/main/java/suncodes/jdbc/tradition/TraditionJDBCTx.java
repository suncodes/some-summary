package suncodes.jdbc.tradition;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class TraditionJDBCTx {
    public static void main(String[] args) throws Exception {
        // 1、加载驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        // 2. 获取连接
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://192.168.6.111:3306/jdbc",
                "jdbc",
                "jdbc"
        );
        // 取消自动提交
        connection.setAutoCommit(false);
        String sql = "insert into my_resource(resource_name,type,address) values (?,?,?)";
        // sql执行对象
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        for (int i = 0; i < 10; i++) {
            preparedStatement.setString(1, "jdbc学习");
            preparedStatement.setString(2, "java");
            preparedStatement.setString(3, "https://blog.csdn.net/qq_36095679/article/details/92725805");
            preparedStatement.addBatch();
        }
        preparedStatement.executeBatch();
        connection.commit();
        connection.setAutoCommit(true);

        preparedStatement.close();
        connection.close();
    }
}
