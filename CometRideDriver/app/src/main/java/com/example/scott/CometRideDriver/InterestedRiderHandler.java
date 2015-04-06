package com.example.scott.CometRideDriver;

import android.location.Location;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Scott on 4/5/2015.
 */
public class InterestedRiderHandler {
    MainActivity main;
    private PrintWriter toServer;
    private BufferedReader fromServer;
    private Socket serverConn;
    private String route;
    ArrayList<InterestedRider> interestedList;
    ArrayList<InterestedRider> clearedEntries;

    public InterestedRiderHandler(Socket conn, String r, MainActivity m) {
        main = m;
        interestedList = new ArrayList<>();
        clearedEntries = new ArrayList<>();
        serverConn = conn;
        route = r;
        try {
            fromServer = new BufferedReader(new InputStreamReader(serverConn.getInputStream()));
            toServer = new PrintWriter(serverConn.getOutputStream());
        } catch (IOException e) {
            //login.bluetoothUpdate("IOException getting streams");
        }
    }

    public void handle() {
        populateInterestedList();
        removeEntries();
    }

    private void populateInterestedList() {
        interestedList.clear();
        toServer.write("get interested\n");
        toServer.write(route + "\n");
        toServer.flush();
        String line = "";
        try {
            line = fromServer.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int num = Integer.parseInt(line);
        for(int i = 0; i < num; i++) {
            try {
                line = fromServer.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            double lat = Double.parseDouble(line);
            try {
                line = fromServer.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            double lon = Double.parseDouble(line);
            interestedList.add(new InterestedRider(lat,lon));
        }
    }

    private void removeEntries() {
        Location loc = main.getLastLoc();
        for(InterestedRider r : interestedList) {
            Location rider = new Location("");
            rider.setLatitude(r.latitude);
            rider.setLongitude(r.longitude);
            if(loc.distanceTo(rider) < 5) {
                clearedEntries.add(r);
            }
        }
        for(InterestedRider r : clearedEntries) {
            toServer.write("remove interested\n");
            toServer.write(route + "\n");
            toServer.write(r.latitude + "\n");
            toServer.write(r.longitude + "\n");
            toServer.flush();
        }
    }


    public class InterestedRider {
        public double latitude;
        public double longitude;

        public InterestedRider(double lat, double lon) {
            latitude = lat;
            longitude = lon;
        }
    }
}
