package suncodes.jdbc.dbutil;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryLoader;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.DriverManager;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class DbUtilDataSource {
    public static void main(String[] args) throws Exception {
        new DbUtilDataSource().f2();
    }

    public DataSource getDataSource() throws Exception {
        // load driver
        DbUtils.loadDriver("com.mysql.cj.jdbc.Driver");

        // get datasource from resource dir
        Properties properties = new Properties();
        properties.load(DbUtilDataSource.class.getClassLoader().getResourceAsStream("druid.properties"));

        // get query handler
        DataSource ds = DruidDataSourceFactory.createDataSource(properties);
        return ds;
    }

    public void f() throws Exception {

        QueryRunner queryRunner = new QueryRunner(getDataSource());

        // sql
        String sql = "select * from my_resource where id = ? or id=?";
        // query result
        MapListHandler mapHandler = new MapListHandler();
        List<Map<String, Object>> query = queryRunner.query(sql, mapHandler,  1, 2);
        for (Map<String, Object> stringObjectMap : query) {
            for (Map.Entry<String, Object> stringObjectEntry : stringObjectMap.entrySet()) {
                System.out.println(stringObjectEntry.getKey());
                System.out.println(stringObjectEntry.getValue());
            }
        }
    }

    public void f1() throws Exception {

        QueryRunner queryRunner = new QueryRunner(getDataSource());

        // sql
        String sql = "insert into my_resource(resource_name,type,address) values(?,?,?)";
        Object[] objects = new Object[]{"Apache的DBUtils使用详解", "java", "https://blog.csdn.net/qq_34416191/article/details/51884266"};
        // query result
        System.out.println(queryRunner.execute(sql, objects));
    }

    public void f2() throws Exception {

        QueryRunner queryRunner = new QueryRunner(getDataSource());

        // sql
        String sql = "delete from my_resource where id = ?";
        Object[][] params = new Object[20][];
        for (int i = 0; i < 20; i++) {
            params[i] = new Object[]{3 + i};
        }
        // query result
        System.out.println(Arrays.toString(queryRunner.batch(sql, params)));
    }
}
