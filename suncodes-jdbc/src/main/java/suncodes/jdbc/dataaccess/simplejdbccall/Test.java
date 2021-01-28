package suncodes.jdbc.dataaccess.simplejdbccall;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import java.sql.Date;
import java.sql.Types;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        simpleJdbcCallFunction();
    }

    public static void simpleJdbcCallProcedure() {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate());
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("in_id", 1);
        Map<String, Object> resource = simpleJdbcCall
                // 过程名
                .withProcedureName("read_resource")
                // 参数，返回值使用map形式
                .execute(sqlParameterSource);
        System.out.println(resource);
    }

    public static void simpleJdbcCallProcedureBean() {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate());
        SqlParameter sqlParameter = new SqlParameter("in_id", Types.NUMERIC);
        SqlOutParameter sqlOutParameter1 = new SqlOutParameter("out_resource", Types.VARCHAR);
        SqlOutParameter sqlOutParameter2 = new SqlOutParameter("out_type", Types.VARCHAR);
        SqlOutParameter sqlOutParameter3 = new SqlOutParameter("out_address", Types.VARCHAR);

        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("in_id", 1);
        Map<String, Object> resource = simpleJdbcCall
                // 过程名
                .withProcedureName("read_resource")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(sqlParameter, sqlOutParameter1, sqlOutParameter2, sqlOutParameter3)
                .execute(sqlParameterSource);
        System.out.println(resource);
    }

    public static void simpleJdbcCallFunction() {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate());
        Date now = simpleJdbcCall
                // 函数名
                .withFunctionName("NOW")
                // 执行，第一个参数为返回值类型，第二个参数为函数的入参
                .executeFunction(Date.class);
        System.out.println(now);
    }

    private static JdbcTemplate jdbcTemplate() {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("jdbctemplate.xml");
        DruidDataSource druidDataSource = context.getBean("druidDataSource", DruidDataSource.class);

        return new JdbcTemplate(druidDataSource);
    }
}
