package suncodes.jdbc.dbutil;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class DbUtilTx {

    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) throws SQLException {
        f();
    }

    private static Connection getConnection() {
        // 加载驱动
        DbUtils.loadDriver("com.mysql.cj.jdbc.Driver");
        // 获取连接
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://192.168.6.111:3306/jdbc",
                    "jdbc", "jdbc");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    private static void startTransaction() {
        Connection connection = getConnection();
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        threadLocal.set(connection);
    }

    private static void commit() {
        DbUtils.commitAndCloseQuietly(threadLocal.get());
        threadLocal.remove();
    }

    private static void f() throws SQLException {
        startTransaction();
        QueryRunner queryRunner = new QueryRunner();
        // sql
        String sql = "select * from my_resource where id = ? or id=?";
        // query result
        MapListHandler mapHandler = new MapListHandler();
        List<Map<String, Object>> query = queryRunner.query(threadLocal.get(), sql, mapHandler,  1, 2);
        for (Map<String, Object> stringObjectMap : query) {
            for (Map.Entry<String, Object> stringObjectEntry : stringObjectMap.entrySet()) {
                System.out.println(stringObjectEntry.getKey());
                System.out.println(stringObjectEntry.getValue());
            }
        }
        commit();
    }
}
