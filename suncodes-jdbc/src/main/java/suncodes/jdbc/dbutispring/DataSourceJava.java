package suncodes.jdbc.dbutispring;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class DataSourceJava {
    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DataSourceJavaConfig.class);
        DataSource dataSource = context.getBean("dataSource", DruidDataSource.class);
        QueryRunner queryRunner = new QueryRunner(dataSource);
        String sql = "select * from my_resource";
        MapListHandler mapListHandler = new MapListHandler();
        List<Map<String, Object>> query = queryRunner.query(sql, mapListHandler);
        System.out.println(query);
    }
}
