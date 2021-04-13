package suncodes.opensource.autowire.annotation.autowired;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class CustomerByName {

    @Qualifier("address")
    @Autowired
    private Address address;

    @Override
    public String toString() {
        return "Customer{" +
                "address=" + address +
                '}';
    }
}