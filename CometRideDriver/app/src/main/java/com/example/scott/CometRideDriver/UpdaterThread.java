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

    private boolean loginReady = false;
    private boolean updateReady = false;
    private boolean running = true;
    private LoginActivity login;
    private MainActivity main;

    private String username;
    private String password;


    public UpdaterThread(LoginActivity login) {
        this.login = login;
    }

    public void setLoginReady() {
        loginReady = true;
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
//        toServer.write("update");
//        toServer.write(riders);
//        toServer.write(loc);
//        toServer.write(status);
//        toServer.flush();
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
}