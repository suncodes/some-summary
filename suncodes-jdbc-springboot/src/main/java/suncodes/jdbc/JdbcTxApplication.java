package suncodes.jdbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

// TODO SpringBoot可以不用加此注解，即可自动开启事务，只要引入了spring-boot-starter-jdbc
//@EnableTransactionManagement
@SpringBootApplication
public class JdbcTxApplication {
    public static void main(String[] args) {
        SpringApplication.run(JdbcTxApplication.class, args);
    }
}
