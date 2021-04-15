package suncodes.opensource;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import suncodes.opensource.tx.Customer;
import suncodes.opensource.tx.CustomerService;
import suncodes.opensource.tx.TxConfig;

public class TxTest {

    @Test
    public void f() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TxConfig.class);
        CustomerService customerService = context.getBean(CustomerService.class);
        Customer customer = new Customer();
        customer.setCustomerName("sunchuizhe");
        customer.setCurrentMoney(200);
        customerService.insert(customer);
    }
}
