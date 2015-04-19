package com.example.scott.CometRideDriver;

import android.location.Location;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * Created by Scott on 3/27/2015.
 */
public class UpdaterThread extends Thread {
    private PrintWriter toServer;
    private BufferedReader fromServer;
    private Socket serverConn;

    private final String Host = "104.154.93.11";
    private final int port = 1500;

    private boolean loginReady = false;
    private boolean updateReady = false;
    private boolean getRouteInfo = false;
    private boolean running = true;

    private MainActivity main;
    private InterestedRiderHandler interestedRiders;

    private Driver driverInfo;

    private ArrayList<String> routeNames;
    private ArrayList<Shuttle> shuttleList;

    public UpdaterThread(MainActivity main) {
        driverInfo = new Driver();
        this.main = main;
    }

    public void setLoginReady() {
        loginReady = true;
    }

    public void setUpdateReady(boolean b) {
        updateReady = b;
    }

    public void setUsername(String u) {
        driverInfo.setUsername(u);
    }

    public void setPassword(String p) {
        driverInfo.setPassword(p);
    }

    public void setRoute(String name) {
        driverInfo.setRouteName(name);
        getRouteInfo = true;
    }

    public void setShuttle(int shuttle) {
        driverInfo.setShuttleNum(shuttle);
    }

    public void run() {
        running = true;
        try {
            serverConn = new Socket(Host, port);
            serverConn.setKeepAlive(true);
        } catch (Exception e) {
            main.bluetoothUpdate(e.getMessage());
        }
        try {
            fromServer = new BufferedReader(new InputStreamReader(serverConn.getInputStream()));
            toServer = new PrintWriter(serverConn.getOutputStream());
        } catch (IOException e) {
            //login.bluetoothUpdate("IOException getting streams");
        }
        shuttleList = getShuttleList();
        routeNames = getRouteList();
        interestedRiders = new InterestedRiderHandler(serverConn,main, driverInfo);
        while(running) {
            if(loginReady) {
                boolean status = validateLogin(driverInfo.getUsername(), driverInfo.getPassword());
                if(status) {
                    main.validLogin();
                }
                else {
                    main.invalidLogin();
                }
                loginReady = false;
            }
            else if(getRouteInfo) {
                requestRouteInfo();
                main.initRoute(driverInfo.getRouteInfo());
                getRouteInfo = false;
            }
            else if(updateReady) {
                if(!driverInfo.hasLocation()) {
                    toServer.write("Keep alive\n");
                    continue;
                }
                sendInfoToServer();
                main.updateCab(driverInfo.getLatitude(), driverInfo.getLongitude());
                interestedRiders.handle();
            }
            else {
                toServer.write("Keep alive\n");
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    private void requestRouteInfo() {
        String routeName = driverInfo.getRouteName();
        toServer.write("route info\n");
        toServer.write(routeName + "\n");
        toServer.flush();
        String lines = "";
        String curves = "";
        String color = "";
        try {
            lines = fromServer.readLine();
            curves = fromServer.readLine();
            color = fromServer.readLine();
        }
        catch (Exception e) { e.printStackTrace(); }
        driverInfo.setRouteInfo(lines, curves, color);
    }

    private ArrayList<Shuttle> getShuttleList() {
        ArrayList<Shuttle> shuttles = new ArrayList<>();
        toServer.write("Get Shuttles\n");
        toServer.flush();
        int numShuttles = 0;
        String num;
        try {
            num = fromServer.readLine();
            numShuttles= Integer.parseInt(num);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(int i = 0; i < numShuttles; i++) {
            num = "";
            String cap = "";
            try {
                num = fromServer.readLine();
                cap = fromServer.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(!num.equals("") && !cap.equals("")) {
                int shuttle = Integer.parseInt(num);
                int capacity = Integer.parseInt(cap);
                shuttles.add(new Shuttle(shuttle,capacity));
            }
        }
        return shuttles;
    }

    private ArrayList<String> getRouteList() {
        ArrayList<String> routeList = new ArrayList<>();
        toServer.write("Get Routes\n");
        toServer.flush();
        int numRoutes = 0;
        String num;
        try {
            num = fromServer.readLine();
            numRoutes = Integer.parseInt(num);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(int i = 0; i < numRoutes; i++) {
            String route = "";
            try {
                route = fromServer.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(!route.equals("")) {
                routeList.add(route);
            }
        }
        return routeList;
    }

    private void sendInfoToServer() {
        toServer.write("update\n");
        toServer.write(driverInfo.getShuttleNum() + "\n");
        toServer.write(driverInfo.getStatus() + "\n");
        toServer.write(driverInfo.getLatitude() + "\n");
        toServer.write(driverInfo.getLongitude() + "\n");
        toServer.write(driverInfo.getRouteName() + "\n");
        toServer.write(driverInfo.getCurrentRiders() + "\n");
        toServer.write(driverInfo.getNewRiders() + "\n");
        toServer.write(driverInfo.getUsername() + "\n");
        toServer.flush();
        driverInfo.resetNewRiders();
        if(!driverInfo.getStatus()) {
            setUpdateReady(false);
        }
    }

    private boolean validateLogin(String user, String pass) {
        boolean status = false;
        toServer.write("Login" + '\n');
        toServer.write(user + '\n');
        String hashedPass = MD5(pass);
        toServer.write(hashedPass + '\n');
        toServer.flush();

        try {
            String Response = fromServer.readLine();
            if(Response.equalsIgnoreCase("true")) {
                status = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return status;
    }

    public void kill() {
        running = false;
        try {
            toServer.write("close\n");
            serverConn.close();
            toServer.close();
            fromServer.close();
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }

    private String MD5(String pwd)  {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if (md != null) {
            md.update(pwd.getBytes());
        }
        byte[] digest = new byte[0];
        if (md != null) {
            digest = md.digest();
        }
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%02x", b & 0xff));
        }
        String md5_pass = sb.toString();
        return md5_pass;
    }

    public ArrayList<String> getRouteNames() {
        return routeNames;
    }

    public ArrayList<Shuttle> getShuttles() {
        return shuttleList;
    }

    public void setDriverStatus(boolean status) {
        driverInfo.setStatus(status);
    }

    public void setCapacity(int cap) {
        driverInfo.setCapacity(cap);
    }

    public void newRider() {
        driverInfo.incrementNewRiders();
    }

    public void setNumRiders(int amount) {
        driverInfo.setCurrentRiders(amount);
    }

    public void setLocation(Location location) {
        driverInfo.setLoc(location);
    }

    public double getLatitude() {
        return driverInfo.getLatitude();
    }

    public double getLongitude() {
        return driverInfo.getLongitude();
    }

    public boolean hasLocation() {
        return driverInfo.hasLocation();
    }
}