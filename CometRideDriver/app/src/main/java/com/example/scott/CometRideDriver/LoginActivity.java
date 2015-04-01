package com.example.scott.CometRideDriver;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.scott.CometRideDriver.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

public class LoginActivity extends ActionBarActivity implements View.OnClickListener {

    private Button btnLoginDialog;

    public UpdaterThread updater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        updater = new UpdaterThread(this);
        updater.start();
        btnLoginDialog = (Button) findViewById(R.id.btnLoginDialog);
        btnLoginDialog.setOnClickListener(this);
        //btnLoginDialog.performClick();
    }

    public void bluetoothUpdate(final String text) {
        this.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

    public void loginResponse(boolean status) {
        if(status) {
            Intent myIntent=new Intent(LoginActivity.this,MainActivity.class);
            startActivity(myIntent);
        }
        else {
            this.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(LoginActivity.this,"Invalid credentials", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        if(v == btnLoginDialog) {
            // Create Object of Dialog class
            final Dialog login = new Dialog(this);
            // Set GUI of login screen
            login.setContentView(R.layout.login_dialog);
            login.setTitle("Login to CometRide");

            // Init button of login GUI
            Button btnLogin = (Button) login.findViewById(R.id.btnLogin);
            Button btnCancel = (Button) login.findViewById(R.id.btnCancel);
            final EditText txtUsername = (EditText)login.findViewById(R.id.txtUsername);
            final EditText txtPassword = (EditText)login.findViewById(R.id.txtPassword);

            // Attached listener for login GUI button
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String username = txtUsername.getText().toString().trim();
                    String password = txtPassword.getText().toString().trim();
                    if(username.length() > 0 && password.length() > 0)
                    {
                        updater.setUsername(username);
                        updater.setPassword(password);
                        updater.setLoginReady();
                        login.dismiss();
                    }
                    else {
                        Toast.makeText(LoginActivity.this,"Please enter Username and Password", Toast.LENGTH_LONG).show();
                    }
                }
            });
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    login.dismiss();
                }
            });

            // Make dialog box visible.
            login.show();
        }
    }
}