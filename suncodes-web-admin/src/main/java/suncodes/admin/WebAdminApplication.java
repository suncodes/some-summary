package suncodes.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;


@MapperScan("suncodes.admin.mapper")
@ServletComponentScan(basePackages = "suncodes.admin")
@SpringBootApplication(exclude = {RedisAutoConfiguration.class})
public class WebAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebAdminApplication.class, args);
    }
}
