package suncodes.opensource.tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CustomerDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public void insert(Customer customer) {
        String sql = "insert into bank(customerName, currentMoney) values (?, ?)";
        jdbcTemplate.update(sql, customer.getCustomerName(), customer.getCurrentMoney());
        int i = 1/0;
    }
}
