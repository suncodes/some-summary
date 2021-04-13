package suncodes.opensource.autowire.annotation.resource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import suncodes.opensource.autowire.annotation.resource.Address;

import javax.annotation.Resource;

@Component
public class CustomerDefault {

    @Resource
    private Address address;

    @Override
    public String toString() {
        return "Customer{" +
                "address=" + address +
                '}';
    }
}