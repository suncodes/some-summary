package suncodes.jdbc.dbutispring;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

/**
 * PropertySource 用于获取peoperties文件的属性，配合Value注解
 */
@Configuration
@PropertySource(value={"classpath:druid.properties"})
public class DataSourceJavaConfig {

    @Bean
    public DataSource dataSource(@Value("${url}") String url,
                                 @Value("${driverClassName}") String driverClassName,
                                 @Value("${jdbc.username}") String username,
                                 @Value("${password}") String password
                                 ) {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(driverClassName);
        druidDataSource.setUrl(url);
        druidDataSource.setUsername(username);
        druidDataSource.setPassword(password);
        return druidDataSource;
    }
}
