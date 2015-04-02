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
import java.util.Calendar;

/**
 * Created by Scott on 3/27/2015.
 */
public class UpdaterThread extends Thread {
    private MainActivity ourActivity;
    private PrintWriter toServer;
    private BufferedReader fromServer;
    private Socket serverConn;

    private String Host = "cs1.utdallas.edu";
    private int port = 16000;

    private boolean loginReady = false;
    private boolean updateReady = false;
    private LoginActivity login;
    private MainActivity main;

    private String username;
    private String password;

    public UpdaterThread(LoginActivity login) {
        this.login = login;
    }

    public void setLoginReady() {
        loginReady = true;
        login.bluetoothUpdate("loginReady set");
    }

    public void setUpdateReady() {
        updateReady = true;
    }

    public void setUsername(String u) {
        username = u;
    }

    public void setPassword(String p) {
        password = p;
    }

    public void openConnection() {
        try {
            serverConn = new Socket(Host, port);
        } catch (IOException e) {
            login.bluetoothUpdate("IOException opening connection");
        }
        try {
            fromServer = new BufferedReader(new InputStreamReader(serverConn.getInputStream()));
            toServer = new PrintWriter(serverConn.getOutputStream());
        } catch (IOException e) {
            login.bluetoothUpdate("IOException getting streams");
        }
    }

    public void run() {
        try {
            serverConn = new Socket("cs1.utdallas.edu", 16000);
            login.bluetoothUpdate(serverConn.getRemoteSocketAddress().toString() + ":" + serverConn.getPort());
            serverConn.setKeepAlive(true);
        } catch (Exception e) {
            login.bluetoothUpdate(e.getMessage());
        }
        //login.bluetoothUpdate(serverConn.getRemoteSocketAddress().toString() + ":" + serverConn.getPort());
        try {
            fromServer = new BufferedReader(new InputStreamReader(serverConn.getInputStream()));
            toServer = new PrintWriter(serverConn.getOutputStream());
        } catch (IOException e) {
            //login.bluetoothUpdate("IOException getting streams");
        }
        //toServer.write("Connected");
        while(true) {
            //login.bluetoothUpdate("Iterate");
            if(loginReady) {
                //login.bluetoothUpdate("Validating Login");
                validateLogin(username, password);
                loginReady = false;
            }
            if(updateReady) {
                int numRiders = ourActivity.getCurrentRiders();
                //ourActivity.bluetoothUpdate(numRiders + " at " + Calendar.getInstance().get(Calendar.SECOND));
                Location currentLoc = ourActivity.getLocation();
                boolean status = ourActivity.getStatus();
                //sendInfoToServer(numRiders, currentLoc, status);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }
    }

    private void sendInfoToServer(int riders, Location loc, boolean status) {
//        toServer.write("update".getBytes());
//        toServer.write(riders);
//        toServer.write(loc);
//        toServer.write(status);
    }

    private void validateLogin(String user, String pass) {
        //Security stuff (Hashing?)
        //Send stuff to server
        boolean status = true;
        toServer.write("Login");
        toServer.write(user);
        toServer.write(pass);

        login.loginResponse(status);
    }

}