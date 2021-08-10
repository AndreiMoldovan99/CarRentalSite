package com.example.demo.entity;

import java.util.HashMap;

public class Park {
    HashMap<Car,Integer> park;

    public Park() {
        this.park = new HashMap<>();
    }

    public HashMap<Car, Integer> getPark() {
        return park;
    }
}
