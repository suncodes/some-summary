package suncodes.opensource.autowire.annotation.autowired;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerByPrimary {

    @Autowired
    private Address address;

    @Override
    public String toString() {
        return "Customer{" +
                "address=" + address +
                '}';
    }
}