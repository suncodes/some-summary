package suncodes.jdbc.txconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@ComponentScan("suncodes.jdbc.txconfig.service")
@Configuration
@ImportResource({"classpath:txAdviceAnnotion.xml"})
//@EnableTransactionManagement
public class TxAdviceConfig {
}
