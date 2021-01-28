package suncodes.jdbc.dataaccess.simpleJdbcInsert;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.KeyHolder;

import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        simpleJdbcInsertByUsingColumns();
    }

    public static void simpleJdbcInsertByMap() {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate());
        Map<String, String> map = new HashMap<>(16);
        map.put("resource_name", "纯情九九 老婆 自慰");
        map.put("type", "video");
        map.put("address", "https://youtu.be/q3VIZ9ZyfeI");
        int update = simpleJdbcInsert
                // 设置表名
                .withTableName("my_resource")
                // 直接根据参数插入数据，参数名需要和数据库中的列一致
                .execute(map);
        System.out.println(update);
    }

    public static void simpleJdbcInsertBySqlParameterSource() {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate());
        Map<String, String> map = new HashMap<>(16);
        map.put("resource_name", "纯情九九 老婆 自慰");
        map.put("type", "video");
        map.put("address", "https://youtu.be/q3VIZ9ZyfeI");
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(map);
        int update = simpleJdbcInsert
                // 设置表名
                .withTableName("my_resource")
                // 直接根据参数插入数据，参数名需要和数据库中的列一致
                .execute(sqlParameterSource);
        System.out.println(update);
    }

    public static void simpleJdbcInsertByGeneratedKeyColumns() {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate());
        Map<String, String> map = new HashMap<>(16);
        map.put("resource_name", "纯情九九 老婆 自慰");
        map.put("type", "video");
        map.put("address", "https://youtu.be/q3VIZ9ZyfeI");
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(map);
        Number update = simpleJdbcInsert
                // 设置表名
                .withTableName("my_resource")
                // 指定自增的键值
                .usingGeneratedKeyColumns("id")
                // 直接根据参数插入数据，参数名需要和数据库中的列一致
                // 同时返回key，需要指定自增的列，不然报错
                .executeAndReturnKey(sqlParameterSource);
        System.out.println(update);
    }

    public static void simpleJdbcInsertByKeyHolder() {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate());
        Map<String, String> map = new HashMap<>(16);
        map.put("resource_name", "纯情九九 老婆 自慰");
        map.put("type", "video");
        map.put("address", "https://youtu.be/q3VIZ9ZyfeI");
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(map);
        // KeyHolder 可以获取非数字类型或多个联合自增的键值
        KeyHolder update = simpleJdbcInsert
                // 设置表名
                .withTableName("my_resource")
                // 指定自增的键值
                .usingGeneratedKeyColumns("id")
                // 直接根据参数插入数据，参数名需要和数据库中的列一致
                // 同时返回key，需要指定自增的列，不然报错
                .executeAndReturnKeyHolder(sqlParameterSource);
        System.out.println(update);
    }

    public static void simpleJdbcInsertByUsingColumns() {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate());
        Map<String, String> map = new HashMap<>(16);
        map.put("resource_name", "纯情九九 老婆 自慰");
        map.put("type", "video");
        map.put("address", "https://youtu.be/q3VIZ9ZyfeI");
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(map);
        Number update = simpleJdbcInsert
                // 设置表名
                .withTableName("my_resource")
                // 指定自增的键值
                .usingGeneratedKeyColumns("id")
                // 当指定了列之后，如果参数多余，则不会插入数据库；如果参数少，没效果
                .usingColumns("resource_name", "type")
                // 直接根据参数插入数据，参数名需要和数据库中的列一致
                // 同时返回key，需要指定自增的列，不然报错
                .executeAndReturnKey(sqlParameterSource);
        System.out.println(update);
    }

    private static JdbcTemplate jdbcTemplate() {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("jdbctemplate.xml");
        DruidDataSource druidDataSource = context.getBean("druidDataSource", DruidDataSource.class);

        return new JdbcTemplate(druidDataSource);
    }
}
