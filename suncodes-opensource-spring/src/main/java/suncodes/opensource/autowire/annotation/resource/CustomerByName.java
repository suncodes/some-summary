package suncodes.opensource.autowire.annotation.resource;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class CustomerByName {

    @Resource(name = "addressByName")
    private Address address;

    @Override
    public String toString() {
        return "Customer{" +
                "address=" + address +
                '}';
    }
}