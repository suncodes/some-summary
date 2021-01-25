package suncodes.jdbc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import suncodes.jdbc.po.ResourcePO;

import java.util.List;

@Service
public class ResourceService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void f() {
        String sql = "select * from my_resource";
        BeanPropertyRowMapper<ResourcePO> mapper = new BeanPropertyRowMapper<>(ResourcePO.class);
        List<ResourcePO> query = jdbcTemplate.query(sql, mapper);
        for (ResourcePO resourcePO : query) {
            System.out.println(resourcePO);
        }
    }

    @Transactional()
    public void add() {
        add("ceshi", "ceshi", "ceshi");
        int i = 1 / 0;
        System.out.println("我是add方法");
        add("ceshi1", "ceshi1", "ceshi1");
    }

    public void add(String resourceName, String type, String address) {
        String sql = "insert into my_resource(resource_name,type,address) values(?,?,?)";
        jdbcTemplate.update(sql, resourceName, type, address);
    }
}
