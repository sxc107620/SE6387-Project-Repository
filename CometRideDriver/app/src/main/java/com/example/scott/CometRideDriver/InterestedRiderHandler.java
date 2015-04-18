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

    public InterestedRiderHandler(Socket conn, MainActivity m) {
        main = m;
        interestedList = new ArrayList<>();
        clearedEntries = new ArrayList<>();
        serverConn = conn;
        try {
            fromServer = new BufferedReader(new InputStreamReader(serverConn.getInputStream()));
            toServer = new PrintWriter(serverConn.getOutputStream());
        } catch (IOException e) {
            //login.bluetoothUpdate("IOException getting streams");
        }
    }

    public void setRoute(String r) {
        this.route = r;
    }

    public void handle() {
        populateInterestedList();
        removeEntries();
        updateInterestedOnMap();
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
        if(!interestedIDs.isEmpty()) {
            interestedIDs = interestedIDs.substring(0, interestedIDs.length() - 1);
            interestedLats = interestedLats.substring(0, interestedLats.length() - 1);
            interestedLons = interestedLons.substring(0, interestedLons.length() - 1);
        }
        main.refreshInterested(interestedIDs, interestedLats, interestedLons);
        interestedList.clear();
        clearedEntries.clear();
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
        clearedEntries.clear();
        Location loc = main.getLastLoc();
        for(InterestedRider r : interestedList) {
            float[] dist = new float[3];
            Location.distanceBetween(loc.getLatitude(), loc.getLongitude(), r.latitude, r.longitude, dist);
            if(dist[0] < 5.0) {
                clearedEntries.add(r);
                interestedList.remove(r);
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
