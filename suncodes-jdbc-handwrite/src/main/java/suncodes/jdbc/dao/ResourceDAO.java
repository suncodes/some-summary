package suncodes.jdbc.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import suncodes.jdbc.annotation.ExtTransaction;

import java.util.List;
import java.util.Map;

@ExtTransaction
@Component
public class ResourceDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void add(String resourceName, String type, String address) {
        String sql = "insert into my_resource(resource_name,type,address) values(?,?,?)";
        jdbcTemplate.update(sql, resourceName, type, address);
    }

    public void query() {
        String sql = "select resource_name from my_resource";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        for (Map<String, Object> map : maps) {
            System.out.println(map);
        }
    }
}
