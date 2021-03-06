package com.example.scott.CometRideDriver;

import android.location.Location;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Scott on 3/27/2015.
 */
public class UpdaterThread extends Thread {
    private PrintWriter toServer;
    private BufferedReader fromServer;
    private Socket serverConn;

    private String Host = "104.154.93.11";
    private int port = 1500;
    private String routeName = "";

    private boolean loginReady = false;
    private boolean updateReady = false;
    private boolean selectRoutes = false;
    private boolean selectShuttle = false;
    private boolean getCapacity = false;
    private boolean getRouteInfo = false;
    private boolean running = true;

    private MainActivity main;
    private InterestedRiderHandler interestedRiders;

    private ArrayList<String> routeNames;
    private ArrayList<Shuttle> shuttleList;
    private String routeLines = "";
    private String routeCurves = "";
    private String routeColor = "";

    private String username;
    private String password;
    private Integer ourShuttle;
    private int shuttleCapacity;

    public String getRouteName() { return routeName; }

    public UpdaterThread(MainActivity main) {
        this.main = main;
    }

    public void setLoginReady() {
        loginReady = true;
    }

    public void setUpdateReady(boolean b) {
        updateReady = b;
    }

    public void setUsername(String u) {
        username = u;
    }

    public void setPassword(String p) {
        password = p;
    }

    public void setRoute(String name) {
        this.routeName = name;
        interestedRiders.setRoute(name);
        getRouteInfo = true;
    }

    public void setShuttle(int shuttle) {
        ourShuttle = shuttle;
    }

    public int getShuttleCapacity() { return shuttleCapacity; }

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
        interestedRiders = new InterestedRiderHandler(serverConn,main);
        while(running) {
            if(loginReady) {
                boolean status = validateLogin(username,password);
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
                main.initRoute(routeColor, routeCurves, routeLines);
                getRouteInfo = false;
            }
            else if(updateReady) {
                int numRiders = main.getCurrentRiders();
                int newRiders = main.getNewRiders();
                Location currentLoc = main.getLocation();
                if(currentLoc == null) {
                    toServer.write("Keep alive\n");
                    continue;
                }
                boolean status = main.getStatus();
                sendInfoToServer(numRiders, newRiders, currentLoc, status);
                main.updateCab(currentLoc.getLatitude(), currentLoc.getLongitude());
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
        toServer.write("route info\n");
        toServer.write(routeName + "\n");
        toServer.flush();
        try {
            routeLines = fromServer.readLine();
            routeCurves = fromServer.readLine();
            routeColor = fromServer.readLine();
        }
        catch (Exception e) {}
    }

    private ArrayList<Shuttle> getShuttleList() {
        ArrayList<Shuttle> shuttles = new ArrayList<>();
        toServer.write("Get Shuttles\n");
        toServer.flush();
        int numShuttles = 0;
        String num = "";
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
        ArrayList<String> routeList = new ArrayList<String>();
        toServer.write("Get Routes\n");
        toServer.flush();
        int numRoutes = 0;
        String num = "";
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

    private void sendInfoToServer(int currentRiders, int newRiders, Location loc, boolean status) {
        toServer.write("update\n");
        toServer.write(ourShuttle + "\n");
        toServer.write(status + "\n");
        toServer.write(loc.getLatitude() + "\n");
        toServer.write(loc.getLongitude() + "\n");
        toServer.write(routeName + "\n");
        toServer.write(currentRiders + "\n");
        toServer.write(newRiders + "\n");
        toServer.write(username + "\n");
        toServer.flush();
        main.resetNewRiders();
        if(!status) {
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
        md.update(pwd.getBytes());
        byte[] digest = md.digest();
        StringBuffer sb = new StringBuffer();
        for (byte b : digest) {
            sb.append(String.format("%02x", b & 0xff));
        }
        String md5_pass = sb.toString();
        return md5_pass;
    }

    public String getRouteLines() {
        return routeLines;
    }

    public String getRouteCurves() {
        return routeCurves;
    }

    public String getRouteColor() {
        return routeColor;
    }

    public ArrayList<String> getRouteNames() {
        return routeNames;
    }

    public ArrayList<Shuttle> getShuttles() {
        return shuttleList;
    }
}