package com.example.scott.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.ParcelUuid;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * Created by Scott on 3/24/2015.
 * This class was mostly me trying to use bluetooth APIs instead of overriding key events. You can ignore it.
 */
public class BluetoothThread extends Thread {
    private final BluetoothSocket mmSocket;
    private final BluetoothDevice mmDevice;
    private final MainActivity ourActivity;

    public BluetoothThread(BluetoothDevice device, MainActivity act) {
        // Use a temporary object that is later assigned to mmSocket,
        // because mmSocket is final
        BluetoothSocket tmp = null;
        mmDevice = device;
        ourActivity = act;
        UUID myUUID = null;
        ParcelUuid[] UUIDs = device.getUuids();
        myUUID = UUIDs[0].getUuid();
        // Get a BluetoothSocket to connect with the given BluetoothDevice
        try {
            // MY_UUID is the app's UUID string, also used by the server code
            tmp = device.createInsecureRfcommSocketToServiceRecord(myUUID);
        } catch (IOException e) { }
        mmSocket = tmp;
        ourActivity.bluetoothUpdate(mmSocket.getRemoteDevice().getName() + " connected");
    }

    public void run() {
        // Cancel discovery because it will slow down the connection
        ourActivity.mBluetoothAdapter.cancelDiscovery();
        ourActivity.bluetoothUpdate("run");

        try {
            // Connect the device through the socket. This will block
            // until it succeeds or throws an exception
            if(!mmSocket.isConnected()) {
                mmSocket.connect();
            }
        } catch (IOException connectException) {
            // Unable to connect; close the socket and get out
            try {
                mmSocket.close();
            } catch (IOException closeException) {
                ourActivity.bluetoothUpdate(closeException.getMessage());
            }
            ourActivity.bluetoothUpdate(connectException.getMessage());
            return;
        }

        // Do work to manage the connection (in a separate thread)
        //manageConnectedSocket(mmSocket);
        ourActivity.bluetoothUpdate("Connection Successful");
    }

    /** Will cancel an in-progress connection, and close the socket */
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) { }
    }
}
