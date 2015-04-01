package com.example.scott.CometRideDriver;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;


public class MainActivity extends Activity implements LocationListener {

    private final static int REQUEST_ENABLE_BT = 1;
    private boolean screenOn = true;

    private LocationManager locMgr;
    private Location lastLoc = null;
    private int currentRiders = 0;
    private int driverStatus = 0;
    private int totalRiders = 0;
    boolean status = true; //Start on duty

    private WebView myBrowser;
    private TextView myText = null;

    private UpdaterThread updater = null;
    private boolean loggedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, "Login Successful. Welcome to CometRide", Toast.LENGTH_SHORT).show();
        /*myText = new TextView(this);
        myText.setText("Start");*/
        //setContentView(myText);
        setUpBluetooth();
        setUpGPS();

        myBrowser = (WebView)findViewById(R.id.mybrowser);
        final MyJavaScriptInterface myJavaScriptInterface = new MyJavaScriptInterface(this);

        myBrowser.addJavascriptInterface(myJavaScriptInterface, "AndroidFunction");
        myBrowser.getSettings().setJavaScriptEnabled(true);

        myBrowser.loadUrl("file:///android_asset/index.html");

        updater = new UpdaterThread(this);
        updater.start();
    }

    public void login(boolean code) {
        loggedIn = code;
    }

    @Override
    public void onLocationChanged(Location location) {
        if(lastLoc == null) {
            lastLoc = location;
            return;
        }
        if((Math.abs(lastLoc.distanceTo(location)) > 0.5)) { //Moved at least 0.5 meters in the last two seconds - In motion
            lastLoc = location;
            blankScreen(true);
        }
        else {
            lastLoc = location;
            blankScreen(false);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled. Press Yes to enable it (GPS required for core functionality)")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private void setUpGPS() {
        locMgr = (LocationManager) getSystemService( Context.LOCATION_SERVICE );
        if ( !locMgr.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Your GPS seems to be disabled. Press Yes to enable it (GPS required for core functionality)")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                            startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                            dialog.cancel();
                        }
                    });
            final AlertDialog alert = builder.create();
            alert.show();
        }
        locMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 0, this);
        lastLoc = locMgr.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    }

    public class MyJavaScriptInterface {
        Context mContext;

        MyJavaScriptInterface(Context c) {
            mContext = c;
        }

        @JavascriptInterface
        public void currentCapacity(String cap) {
            //Database connection goes here, variable cap has the current capacity
            currentRiders = Integer.parseInt(cap);
        }

        public void currentStatus(String stat) {
            //Database connection goes here, Driver status is received here
            driverStatus = Integer.parseInt(stat);
        }


    }

    private void setUpBluetooth() { //This method checks that the clicker exists and is paired
        final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
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

    //This is a debug/output function for me to test things with
    public void bluetoothUpdate(final String text) {
        this.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean getStatus() {
        return status;
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

    private void blankScreen(boolean inMotion) {
        if (inMotion && screenOn) {
            //Turn off screen
            myBrowser.loadUrl("javascript:screenOff()");
            screenOn = false;
        } else if(!inMotion && !screenOn) {
            //Turn on screen.
            myBrowser.loadUrl("javascript:screenOn()");
            screenOn = true;
        }
    }

    protected void onPause() {
        super.onPause();
        locMgr.removeUpdates(this);
    }

    protected void onResume() {
        super.onResume();
        locMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 0, this);
    }

    protected void onStop() {
        super.onStop();
        locMgr.removeUpdates(this);
        updater.interrupt();
        //btThread.cancel();
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

    public int getCurrentRiders() {
        return currentRiders;
    }

    public Location getLocation() {
        return lastLoc;
    }
}
