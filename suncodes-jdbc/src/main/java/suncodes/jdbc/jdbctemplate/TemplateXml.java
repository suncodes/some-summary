package suncodes.jdbc.jdbctemplate;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TemplateXml {
    public static void main(String[] args) {
        queryList1();
    }

    private static JdbcTemplate jdbcTemplate() {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("jdbctemplate.xml");
        DruidDataSource druidDataSource = context.getBean("druidDataSource", DruidDataSource.class);

        return new JdbcTemplate(druidDataSource);
    }

    private static void insert() {
        JdbcTemplate jdbcTemplate = jdbcTemplate();
        String sql = "insert into my_resource(resource_name,type,address) values(?,?,?)";
        Object[] objects = new Object[3];
        objects[0] = "私人玩物";
        objects[1] = "video";
        objects[2] = "https://youtu.be/iOvot6MhyIM";
        jdbcTemplate.update(sql, objects);
    }

    private static void update() {
        JdbcTemplate jdbcTemplate = jdbcTemplate();
        String sql = "update my_resource set type=? where resource_name=?";
        Object[] objects = new Object[2];
        objects[0] = "yellow-video";
        objects[1] = "私人玩物";
        jdbcTemplate.update(sql, objects);
    }

    private static void delete() {
        JdbcTemplate jdbcTemplate = jdbcTemplate();
        String sql = "delete from my_resource where id=?";
        Object[] objects = new Object[1];
        objects[0] = "25";
        jdbcTemplate.update(sql, objects);
    }

    private static void batchUpdate() {
        JdbcTemplate jdbcTemplate = jdbcTemplate();
        String sql = "insert into my_resource(resource_name,type,address) values(?,?,?)";
        Object[] objects = new Object[3];
        objects[0] = "纯情九九 露奶 定制";
        objects[1] = "video";
        objects[2] = "https://youtu.be/7aOHTPKUw5w";
        Object[] objects1 = new Object[3];
        objects1[0] = "纯情九九 露奶自摸 定制";
        objects1[1] = "video";
        objects1[2] = "https://youtu.be/aCS6PRdrMdc";
        List<Object[]> list = new ArrayList<>();
        list.add(objects);
        list.add(objects1);
        jdbcTemplate.batchUpdate(sql, list);
    }

    private static void query() {
        JdbcTemplate jdbcTemplate = jdbcTemplate();
        String sql = "select * from my_resource where id=1";
        BeanPropertyRowMapper<MyResource> rowMapper = new BeanPropertyRowMapper<>(MyResource.class);
        MyResource myResource = jdbcTemplate.queryForObject(sql, rowMapper);
        System.out.println(myResource);
    }

    private static void queryList() {
        JdbcTemplate jdbcTemplate = jdbcTemplate();
        String sql = "select * from my_resource";
        BeanPropertyRowMapper<MyResource> rowMapper = new BeanPropertyRowMapper<>(MyResource.class);
        List<MyResource> myResources = jdbcTemplate.query(sql, rowMapper);
        for (MyResource myResource : myResources) {
            System.out.println(myResource);
        }
    }

    private static void queryList1() {
        JdbcTemplate jdbcTemplate = jdbcTemplate();
        String sql = "select resource_name from my_resource";
        List<String> myResources = jdbcTemplate.queryForList(sql, String.class);
        System.out.println(myResources);
    }

    public static class MyResource {
        private Integer id;
        private String resourceName;
        private String type;
        private String address;

        public MyResource() {
        }

        public MyResource(Integer id, String resourceName, String type, String address) {
            this.id = id;
            this.resourceName = resourceName;
            this.type = type;
            this.address = address;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getResourceName() {
            return resourceName;
        }

        public void setResourceName(String resourceName) {
            this.resourceName = resourceName;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        @Override
        public String toString() {
            return "MyResource{" +
                    "id=" + id +
                    ", resourceName='" + resourceName + '\'' +
                    ", type='" + type + '\'' +
                    ", address='" + address + '\'' +
                    '}';
        }
    }
}
