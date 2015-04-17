package com.example.scott.CometRideDriver;

/**
 * Created by Scott on 4/17/2015.
 */
public class Shuttle {
    private final int number;
    private final int capacity;

    public Shuttle(int n, int c) {
        number = n;
        capacity = c;
    }

    public int getNumber() {
        return number;
    }

    public int getCapacity() {
        return capacity;
    }
}
