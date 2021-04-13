package suncodes.opensource.autowire.annotation.resource;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class CustomerByType {

    @Resource(type = Address.class)
    private Address address;

    @Override
    public String toString() {
        return "Customer{" +
                "address=" + address +
                '}';
    }
}