package suncodes.opensource.autowire.xml;

public class Address {

    private String fulladdress;

    public Address() {

    }

    public Address(String addr) {
        this.fulladdress = addr;
    }

    public String getFulladdress() {
        return fulladdress;
    }

    public void setFulladdress(String fulladdress) {
        this.fulladdress = fulladdress;
    }

    @Override
    public String toString() {
        return "Address{" +
                "fulladdress='" + fulladdress + '\'' +
                '}';
    }
}