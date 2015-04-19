package com.example.scott.CometRideDriver;

import android.location.Location;

/**
 * Created by Scott on 4/19/2015.
 */
public class Driver {
    private String routeName = "";
    private String routeLines = "";
    private String routeCurves = "";
    private String routeColor = "";

    private String username = "";
    private String password = "";
    private int shuttleNum = 0;

    private boolean status = true;
    private Location loc = null;

    private int currentRiders = 0;
    private int newRiders = 0;
    private int capacity = 0;

    public String[] getRouteInfo() {
        String[] info = new String[4];
        info[0] = routeName;
        info[1] = routeLines;
        info[2] = routeCurves;
        info[3] = routeColor;
        return info;
    }

    public void setRouteInfo(String lines, String curves, String color) {
        routeLines = lines;
        routeCurves = curves;
        routeColor = color;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getShuttleNum() {
        return shuttleNum;
    }

    public void setShuttleNum(Integer shuttleNum) {
        this.shuttleNum = shuttleNum;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setLoc(Location loc) {
        this.loc = loc;
    }

    public double getLatitude() {
        return loc.getLatitude();
    }

    public double getLongitude() {
        return loc.getLongitude();
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getCurrentRiders() {
        return currentRiders;
    }

    public void setCurrentRiders(int amount) {
        if(amount <= capacity) {
            currentRiders = amount;
        }
    }

    public void incrementNewRiders() {
        newRiders++;
    }

    public void resetNewRiders() {
        newRiders = 0;
    }

    public int getNewRiders() {
        return newRiders;
    }

    public boolean hasLocation() {
        return loc != null;
    }
}
