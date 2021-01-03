package suncodes.jdbc.jdbctemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * jdbcTemplate java 配置
 */
@Configuration
@ImportResource(locations = "classpath:jdbctemplate.xml")
public class TemplateJavaConfig {

    @Qualifier("druidDataSource")
    @Autowired
    private DataSource dataSource;

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
