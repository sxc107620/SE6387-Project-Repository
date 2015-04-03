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
    private MainActivity ourActivity;
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
    private boolean running = true;

    private LoginActivity login;
    private MainActivity main;
    private RouteActivity route;
    private ShuttleActivity shuttle;

    private String username;
    private String password;
    private Integer ourShuttle;
    private int shuttleCapacity = 7;
    private boolean getCapacity = false;


    public UpdaterThread(LoginActivity login) {
        this.login = login;
    }

    public void setLoginReady() {
        loginReady = true;
    }

    public void getSetCapacity() {
        getCapacity = true;
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

    public void setRouteActivity(RouteActivity route) {
        this.route = route;
    }

    public void setRoute(String name) {
        route.routeSet();
        this.routeName = name;
    }

    public int getShuttleCapacity() { return shuttleCapacity; }

    public void setMainActivity(MainActivity main) {
        this.main = main;
    }

    public void setShuttleActivity(ShuttleActivity shuttle) {
        this.shuttle = shuttle;
    }

    public void run() {
        try {
            serverConn = new Socket(Host, port);
            serverConn.setKeepAlive(true);
        } catch (Exception e) {
            login.bluetoothUpdate(e.getMessage());
        }
        try {
            fromServer = new BufferedReader(new InputStreamReader(serverConn.getInputStream()));
            toServer = new PrintWriter(serverConn.getOutputStream());
        } catch (IOException e) {
            //login.bluetoothUpdate("IOException getting streams");
        }
        //toServer.write("Connected");
        while(running) {
            //login.bluetoothUpdate("Iterate");
            if(loginReady) {
                validateLogin(username, password);
                loginReady = false;
            }
            if(selectRoutes) {
                ArrayList<String> routeList = getRouteList();
                route.selectRoute(routeList);
                selectRoutes = false;
            }
            if(selectShuttle) {
                ArrayList<Integer> shuttleList = getShuttleList();
                shuttle.selectShuttle(shuttleList);
                selectShuttle = false;
            }
            if(getCapacity) {
                getCapacity(ourShuttle);
                getCapacity = false;
            }
            if(updateReady) {
                int numRiders = main.getCurrentRiders();
                int newRiders = main.getNewRiders();
                Location currentLoc = main.getLocation();
                boolean status = main.getStatus();
                sendInfoToServer(numRiders, newRiders, currentLoc, status);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    private void getCapacity(Integer ourShuttle) {
        int capacity;
        toServer.write("Capacity\n");
        toServer.write(ourShuttle.toString() + "\n");
        toServer.flush();
        String Line = null;
        try {
            Line = fromServer.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        char num = Line.charAt(0);
        capacity = Character.getNumericValue(num);
        shuttleCapacity = capacity;
    }

    private ArrayList<Integer> getShuttleList() {
        ArrayList<Integer> shuttles = new ArrayList<Integer>();
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
            String line = "";
            try {
                line = fromServer.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(!line.equals("")) {
                Integer shuttle = Integer.parseInt(line);
                shuttles.add(shuttle);
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
        toServer.flush();
        main.resetNewRiders();
        if(!status) {
            setUpdateReady(false);
        }
    }

    private void validateLogin(String user, String pass) {
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

        login.loginResponse(status);
    }

    public void kill() {
        running = false;
        try {
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

    public void setSelectRoutes() {
        selectRoutes = true;
    }

    public void setSelectShuttle() {
        selectShuttle = true;
    }

    public void setShuttle(Integer shut) {
        ourShuttle = shut;
        shuttle.shuttleSet();
    }
}