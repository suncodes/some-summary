package suncodes.opensource.autowire.xml;

public class Customer {

    private Address address;

    public Customer() {

    }

    public Customer( Address address) {
        super();
        this.address = address;
    }

    public Customer(int id, Address address) {
        super();
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "address=" + address +
                '}';
    }
}