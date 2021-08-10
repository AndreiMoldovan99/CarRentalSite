package com.example.demo.entity;

public class Car {

    private String name;
    private Double value = 0.0;
    private int stock;
    //private boolean stock2;

    public Car(){

    }

    public Car(String name, int stock, double value) {
        this.name = name;
        this.stock = stock;
        //this.stock2 = stock2;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Car{" +
                "name='" + name + '\'' +
                ", value=" + value +
                ", stock=" + stock +
                '}';
    }

    public void add_stock(int add) {
        this.stock += add;
    }

    public void deduct_stock(int deduct) {
        this.stock -= deduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    /*
    public boolean isStock2() {
        return stock2;
    }

    public void setStock2(boolean stock2) {
        this.stock2 = stock2;
    }
    */

}
