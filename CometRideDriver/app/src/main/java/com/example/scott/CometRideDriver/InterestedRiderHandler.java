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
        updateInterestedOnMap();
        clearOldOnMap();
    }

    private void clearOldOnMap() {
        String removeIDs = "";
        for(InterestedRider i : clearedEntries) {
            removeIDs += i.id + ",";
        }
        removeIDs.substring(0,removeIDs.length());
        main.removeInterested(removeIDs);
    }

    private void updateInterestedOnMap() {
        String interestedIDs = "";
        String interestedLats = "";
        String interestedLons = "";
        for(InterestedRider i : interestedList) {
            interestedIDs += i.id + ",";
            interestedLats += i.latitude + ",";
            interestedLons += i.longitude + ",";
        }
        interestedIDs = interestedIDs.substring(0,interestedIDs.length());
        interestedLats = interestedLats.substring(0,interestedLats.length());
        interestedLons = interestedLons.substring(0,interestedLons.length());
        main.addInterested(interestedIDs,interestedLats,interestedLons);
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
            int id = Integer.parseInt(line);
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
            interestedList.add(new InterestedRider(id, lat,lon));
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
            toServer.write(r.id + "\n");
            toServer.flush();
        }
    }


    public class InterestedRider {
        public double latitude;
        public double longitude;
        public int id;

        public InterestedRider(int id, double lat, double lon) {
            this.id = id;
            latitude = lat;
            longitude = lon;
        }
    }
}
