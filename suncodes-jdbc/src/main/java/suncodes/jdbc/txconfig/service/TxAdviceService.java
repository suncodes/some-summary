package suncodes.jdbc.txconfig.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class TxAdviceService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

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
