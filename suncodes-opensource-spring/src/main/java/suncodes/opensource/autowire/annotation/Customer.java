package suncodes.opensource.autowire.annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Customer {

    @Autowired
    private Address address;

//    public Customer() {
//
//    }

//    public Customer(Address address) {
//        super();
//        this.address = address;
//    }

//    public Customer(int id, Address address) {
//        super();
//        this.address = address;
//    }
//
//    public Address getAddress() {
//        return address;
//    }
//
//    public void setAddress(Address address) {
//        this.address = address;
//    }

    @Override
    public String toString() {
        return "Customer{" +
                "address=" + address +
                '}';
    }
}