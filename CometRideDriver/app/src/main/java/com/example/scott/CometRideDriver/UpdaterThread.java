package com.example.scott.CometRideDriver;

import android.location.Location;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;

/**
 * Created by Scott on 3/27/2015.
 */
public class UpdaterThread extends Thread {
    private MainActivity ourActivity;
    private OutputStream toServer;
    private InputStream fromServer;
    private URL serverURL;
    private URLConnection serverConn;

    public UpdaterThread(MainActivity act) {
        ourActivity = act;
        openConnection();
    }

    public void openConnection() {
        int response = -1;
        try {
            serverURL = new URL("Server Location");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            serverConn = serverURL.openConnection();
            fromServer = serverConn.getInputStream();
            toServer = serverConn.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        try {
//            HttpURLConnection httpConn = (HttpURLConnection) serverConn; //httpConn object is assigned the value of serverConn. Typecasting is done to avoid conflict.
//            httpConn.setAllowUserInteraction(false);
//            httpConn.setInstanceFollowRedirects(true);
//            httpConn.setRequestMethod("GET");
//            httpConn.connect();
//            response = httpConn.getResponseCode();
//
//            if (response == HttpURLConnection.HTTP_OK) {
//                fromServer = httpConn.getInputStream();
//                toServer = httpConn.getOutputStream();
//            }
//        }
//        catch (Exception ex)
//        {
//            ex.printStackTrace();
//        }

    }

    public void run() {
        while(true) {
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

    private void sendInfoToServer(int riders, Location loc, boolean status) {
//        toServer.write("update".getBytes());
//        toServer.write(riders);
//        toServer.write(loc);
//        toServer.write(status);
    }

}