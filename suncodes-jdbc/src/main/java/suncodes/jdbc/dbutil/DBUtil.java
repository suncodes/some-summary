package suncodes.jdbc.dbutil;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 使用Apache的工具类
 */
public class DBUtil {
    public static void main(String[] args) throws SQLException {
        // 加载驱动
        DbUtils.loadDriver("com.mysql.cj.jdbc.Driver");
        // 获取连接
        Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.6.111:3306/jdbc",
                "jdbc", "jdbc");
        // SQL执行对象
        QueryRunner queryRunner = new QueryRunner();

        String sql = "select * from my_resource";
        // 查询
        MapListHandler mapHandler = new MapListHandler();
        List<Map<String, Object>> query = queryRunner.query(connection, sql, mapHandler);
        for (Map<String, Object> stringObjectMap : query) {
            for (Map.Entry<String, Object> stringObjectEntry : stringObjectMap.entrySet()) {
                System.out.println(stringObjectEntry.getKey());
                System.out.println(stringObjectEntry.getValue());
            }
        }

        DbUtils.closeQuietly(connection);
    }
}
