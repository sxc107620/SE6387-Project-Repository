package com.example.scott.CometRideDriver;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.scott.CometRideDriver.R;

import java.util.ArrayList;

public class ShuttleActivity extends ActionBarActivity {

    public static UpdaterThread updater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shuttle);
        updater = LoginActivity.updater;
        updater.setShuttleActivity(ShuttleActivity.this);
        updater.setSelectShuttle();
    }

    public void bluetoothUpdate(final String text) {
        this.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void onStart() {
        super.onStart();
        updater.setShuttleActivity(ShuttleActivity.this);
    }

    public void shuttleSet() {
        Intent myIntent = new Intent(ShuttleActivity.this,MainActivity.class);
        startActivity(myIntent);
    }

    public void selectShuttle(final ArrayList<Integer> shuttleList) {
        this.runOnUiThread(new Runnable() {
            public void run() {
                String Route;
                final AlertDialog.Builder selector = new AlertDialog.Builder(ShuttleActivity.this);
                selector.setTitle("Select your Shuttle");
                ArrayList<String> shuttleStrings = new ArrayList<String>();
                for(Integer s : shuttleList) {
                    shuttleStrings.add(s.toString());
                }
                selector.setSingleChoiceItems(shuttleStrings.toArray(new String[shuttleStrings.size()]), 0, null);
                selector.setPositiveButton(R.string.select_shuttle_button_label, new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                                int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                                setShuttle(shuttleList, selectedPosition);
                                // Do something useful withe the position of the selected radio button
                            }
                        }

                );
                selector.show();
            }
        });
    }

    private void setShuttle(ArrayList<Integer> shuttleList, int selectedPosition) {
        updater.setShuttle(shuttleList.get(selectedPosition));
        updater.setGetCapacity();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shuttle, menu);
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
}
