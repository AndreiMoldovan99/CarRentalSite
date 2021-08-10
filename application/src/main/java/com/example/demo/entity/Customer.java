package com.example.demo.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Customer {
    private String Firstname;
    private String Lastname;
    private String email;
    private String phone;
    private String address;

    //Every customer has their individual "park" for renting
    private Double totalbill = 0.0;

    private Park park;

    public Customer() {
    }

    /*private List<Car> items;
    private List<Customer> customers;
    private HashMap<Date,Customer> invoice;*/

    public Customer(String firstname, String lastname, String email, String phone, String address, Park park) {
        Firstname = firstname;
        Lastname = lastname;
        this.email = email;
        this.phone = phone;
        this.address = address;

        this.park = park;

        /*this.customers = new ArrayList<>();
        this.items = new ArrayList<>();
        this.invoice = new HashMap<>();*/
    }

    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String firstname) {
        Firstname = firstname;
    }

    public String getLastname() {
        return Lastname;
    }

    public void setLastname(String lastname) {
        Lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Park getPark() {
        return park;
    }

    public void setPark(Park park) {
        this.park = park;
    }

    public Double getTotalbill() {
        return totalbill;
    }

    public void setTotalbill(Double totalbill) {
        this.totalbill = totalbill;
    }
}
