package com.example.scott.CometRideDriver;

import android.location.Location;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Scott on 3/27/2015.
 */
public class UpdaterThread extends Thread {
    private MainActivity ourActivity;

    public UpdaterThread(MainActivity act) {
        ourActivity = act;
    }

    public void run() {
        while(true) {
            int numRiders = ourActivity.getCurrentRiders();
            ourActivity.bluetoothUpdate(numRiders + " at " + Calendar.getInstance().get(Calendar.SECOND));
            Location currentLoc = ourActivity.getLocation();
            //sendInfoToServer(numRiders, currentLoc);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    private void sendInfoToServer(int riders, Location loc) {
        //Stub Method
    }

}