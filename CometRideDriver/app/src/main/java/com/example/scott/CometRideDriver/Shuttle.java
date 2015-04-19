package com.example.scott.CometRideDriver;

/**
 * Created by Scott on 4/17/2015.
 */

//This class only exists to store both of these numbers in one place so I can make an arraylist of it.
//I could do away with this class by using a map<int,int> instead, but I think this is cleaner.
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
