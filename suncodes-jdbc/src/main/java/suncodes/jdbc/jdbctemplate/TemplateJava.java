package suncodes.jdbc.jdbctemplate;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class TemplateJava {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(TemplateJavaConfig.class);
        JdbcTemplate jdbcTemplate = context.getBean("jdbcTemplate", JdbcTemplate.class);

        String sql = "select * from my_resource";
        BeanPropertyRowMapper<TemplateXml.MyResource> rowMapper = new BeanPropertyRowMapper<>(TemplateXml.MyResource.class);
        List<TemplateXml.MyResource> myResources = jdbcTemplate.query(sql, rowMapper);
        for (TemplateXml.MyResource myResource : myResources) {
            System.out.println(myResource);
        }
    }
}
