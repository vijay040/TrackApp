package com.example.lenovo.trackapp.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.trackapp.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AttandanceActivity extends AppCompatActivity implements LocationListener {
    ImageView signin;
    TextView textViewsignin;
    LocationManager locationManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attandance);
        signin = findViewById(R.id.imageView);
        textViewsignin = findViewById(R.id.textview_signin);
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
        final String createddate = df.format(Calendar.getInstance().getTime());

    }


    @Override
    public void onLocationChanged(Location location) {

        Log.e("location","location************"+location);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}