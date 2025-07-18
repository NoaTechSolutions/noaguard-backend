package com.noatechsolutions.noaguard.dto;

import com.noatechsolutions.noaguard.entity.Address;

public class AddressResponse {

    private String street;
    private String city;
    private String state;
    private String zipCode;
    private String country;

    public AddressResponse() {
    }

    // Constructor desde Address (entidad embebida)
    public AddressResponse(Address address) {
        if (address != null) {
            this.street = address.getStreet();
            this.city = address.getCity();
            this.state = address.getState();
            this.zipCode = address.getZipCode();
            this.country = address.getCountry();
        }
    }

    // Getters y setters

    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getZipCode() { return zipCode; }
    public void setZipCode(String zipCode) { this.zipCode = zipCode; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
}