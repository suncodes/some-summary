package suncodes.opensource.tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {

    @Autowired
    private CustomerDao customerDao;


    public void insert(Customer customer) {
        System.out.println("开始插入...");
        customerDao.insert(customer);
        System.out.println("插入成功");
    }
}
