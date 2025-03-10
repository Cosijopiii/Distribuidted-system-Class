package Sockets.TCP.example2;

import java.io.Serializable;

class Address implements Serializable {
    private static final long serialVersionUID = 1L;
    String street;
    String city;
    String country;

    public Address(String street, String city, String country) {
        this.street = street;
        this.city = city;
        this.country = country;
    }

    @Override
    public String toString() {
        return "Address{street='" + street + "', city='" + city + "', country='" + country + "'}";
    }
}
