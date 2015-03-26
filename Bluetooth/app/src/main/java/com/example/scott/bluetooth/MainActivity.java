package com.example.scott.bluetooth;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Parcel;
import android.os.ParcelUuid;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.bluetooth.BluetoothAdapter;
import android.view.View;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.webkit.WebView;
import android.widget.EditText;

import java.util.Set;
import java.util.UUID;


public class MainActivity extends Activity implements SensorEventListener {

    private final static int REQUEST_ENABLE_BT = 1;
    private TextView myText = null;
    public BluetoothAdapter mBluetoothAdapter = null;
    //private BluetoothThread btThread;
    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    private long lastUpdate = 0;
    private float last_x, last_y, last_z;
    private static final int MOTION_THRESHOLD = 100; //Can be adjusted if necessary
    private boolean screenOn = true;

    WebView myBrowser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*myText = new TextView(this);
        myText.setText("Start");*/
        //setContentView(myText);
        setUpBluetooth();
        setUpAccelerometer();


        myBrowser = (WebView)findViewById(R.id.mybrowser);
        final MyJavaScriptInterface myJavaScriptInterface = new MyJavaScriptInterface();

        myBrowser.addJavascriptInterface(myJavaScriptInterface, "AndroidFunction");
        myBrowser.getSettings().setJavaScriptEnabled(true);

        myBrowser.loadUrl("file:///android_asset/index.html");
    }

    public class MyJavaScriptInterface {

        @JavascriptInterface
        public void currentCapacity(String cap) {
            String.valueOf(cap);
            //Database connection goes here, variable cap has the current capacity
        }


    }


    private void setUpAccelerometer() { //Set up accelerometer stuff
        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void setUpBluetooth() { //This method checks that the clicker exists and is paired
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!mBluetoothAdapter.isEnabled()) { //Ask to enable bluetooth if it's not
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() < 1) {
            /*myText.setText("No Devices");
            setContentView(myText);*/
            return;
        }
        BluetoothDevice clicker = null;
        for (BluetoothDevice bd : pairedDevices) { //Check list of devices for the clicker
            if (bd.getBluetoothClass().getMajorDeviceClass() == BluetoothClass.Device.Major.PERIPHERAL
                    && bd.getName().equals("POP Multimedia"))
                clicker = bd;
        }
        mBluetoothAdapter.cancelDiscovery();
        if (clicker != null) { //This stuff was me trying to use the actual bluetooth API
//            UUID myUUID = null;
//            ParcelUuid[] UUIDs = clicker.getUuids();
//            if (UUIDs.length < 1) {
//                myText.setText("No UUIDs");
//                setContentView(myText);
//                return;
//            } else {
//                myUUID = UUIDs[0].getUuid();
//                //btThread = new BluetoothThread(clicker, this);
//                //btThread.run();
//            }
        }
        else {
            myText.setText("Clicker not found");
            setContentView(myText);
        }
    }

    public void bluetoothUpdate(String text) {
        /*myText.setText(myText.getText() + "\n" + text);
        setContentView(myText);*/
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int keyCode = event.getKeyCode();
        if (event.getAction() == KeyEvent.ACTION_UP) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_VOLUME_UP:
                    //bluetoothUpdate("Volume Up pressed");
                    myBrowser.loadUrl("javascript:inc()");
                    return true;
                case KeyEvent.KEYCODE_VOLUME_DOWN:
                    //bluetoothUpdate("Volume Down pressed");
                    myBrowser.loadUrl("javascript:dec()");
                    return true;
                case KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE:
                    //bluetoothUpdate("Play/Pause pressed");
                    myBrowser.loadUrl("javascript:res()");
                    return true;
                case KeyEvent.KEYCODE_MEDIA_NEXT:
                    bluetoothUpdate("Media Next pressed");
                    return true;
                case KeyEvent.KEYCODE_MEDIA_PREVIOUS:
                    bluetoothUpdate("Media Previous pressed");
                    return true;
                default:
                    return super.dispatchKeyEvent(event);
            }
        } else { //We only want to act on one of key up/down
            switch (keyCode) {
                case KeyEvent.KEYCODE_VOLUME_UP:
                case KeyEvent.KEYCODE_VOLUME_DOWN:
                case KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE:
                case KeyEvent.KEYCODE_MEDIA_NEXT:
                case KeyEvent.KEYCODE_MEDIA_PREVIOUS:
                    return true;
                default:
                    return super.dispatchKeyEvent(event);
            }
        }
    }

    public void onSensorChanged(SensorEvent event) { //This code borrowed from StackOverflow
        //Basically it detects whether the phone is moving using accelerometer data
        Sensor mySensor = event.sensor;
        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            long curTime = System.currentTimeMillis();

            if ((curTime - lastUpdate) > 100) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;

                float speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000;
                if (speed > MOTION_THRESHOLD) {
                    blankScreen(true);
                } else {
                    blankScreen(false);
                }

                last_x = x;
                last_y = y;
                last_z = z;
            }
        }
    }

    public void onAccuracyChanged(Sensor s, int accuracy) {
        //This method intentionally left blank
    }

    private void blankScreen(boolean inMotion) {
        if (inMotion && screenOn) {
            //Turn off screen. Right now I just write a message saying to.
            //bluetoothUpdate("Turn off screen");
            myBrowser.loadUrl("javascript:screenOff()");
            screenOn = false;
        } else if(!inMotion && !screenOn) {
            //Turn on screen. Just a message at the moment.
            //bluetoothUpdate("Turn on screen");
            myBrowser.loadUrl("javascript:screenOn()");
            screenOn = true;
        }
    }

    protected void onPause() {
        super.onPause();
        senSensorManager.unregisterListener(this);
    }

    protected void onResume() {
        super.onResume();
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void onStop() {
        super.onStop();
        //btThread.cancel();
    }
}
