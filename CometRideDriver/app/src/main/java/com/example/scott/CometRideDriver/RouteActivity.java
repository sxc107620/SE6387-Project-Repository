package com.example.scott.CometRideDriver;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.scott.CometRideDriver.R;

import java.util.ArrayList;

public class RouteActivity extends ActionBarActivity {

    public static UpdaterThread updater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        updater = LoginActivity.updater;
        updater.setRouteActivity(RouteActivity.this);
        updater.setSelectRoutes();
    }

    protected void onStart() {
        super.onStart();
        updater.setRouteActivity(RouteActivity.this);
    }

    public void routeSet() {
        Intent myIntent = new Intent(RouteActivity.this,ShuttleActivity.class);
        startActivity(myIntent);
    }

    public void selectRoute(final ArrayList<String> routeList) {
        this.runOnUiThread(new Runnable() {
            public void run() {
                String Route;
                final AlertDialog.Builder selector = new AlertDialog.Builder(RouteActivity.this);
                selector.setTitle("Select your Route");
                selector.setSingleChoiceItems(routeList.toArray(new String[routeList.size()]), 0, null);
                selector.setPositiveButton(R.string.select_route_button_label, new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                                int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                                setRoute(routeList, selectedPosition);
                                // Do something useful withe the position of the selected radio button
                            }
                        }

                );
                selector.show();
            }
        });
    }

    public void setRoute(ArrayList<String> list, int which) {
        updater.setRoute(list.get(which));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_route, menu);
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
