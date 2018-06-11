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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.trackapp.R;
import com.example.lenovo.trackapp.model.AttandenceModel;
import com.example.lenovo.trackapp.model.LoginModel;
import com.example.lenovo.trackapp.model.ResAttandance;
import com.example.lenovo.trackapp.model.ResMetaMeeting;
import com.example.lenovo.trackapp.util.AppLocationService;
import com.example.lenovo.trackapp.util.MyLocation;
import com.example.lenovo.trackapp.util.Shprefrences;
import com.example.lenovo.trackapp.util.Singleton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AttandanceActivity extends AppCompatActivity {
    ImageView signin;
    TextView textViewsignin;
    LocationManager locationManager;
    public static String currentLocation;
    private String status = "signin";
    boolean isLogin = false;
    Shprefrences sh;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sh = new Shprefrences(this);
        setContentView(R.layout.activity_attandance);
        progress = findViewById(R.id.progress);
        signin = findViewById(R.id.imageView);
        textViewsignin = findViewById(R.id.textview_signin);
        progress.setVisibility(View.VISIBLE);
        getAttandanceStatus();
        getLocation();
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postAttandance();
            }
        });
    }

    AppLocationService appLocationService;
    Location nwLocation;

    public void getLocation() {
        appLocationService = new AppLocationService(AttandanceActivity.this);
        nwLocation = appLocationService.getLocation(LocationManager.NETWORK_PROVIDER);

        if (nwLocation != null) {
            MyLocation.LocationResult locationResult = new MyLocation.LocationResult() {
                @Override
                public void gotLocation(Location location) {
                    //Got the location!
                    try {
                        if (location != null) {
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();
                            currentLocation = GetAddress(latitude, longitude);
                            // text_location.setText(location);
                            Log.e("Loaction********", currentLocation);
                        }
                    } catch (Exception e) {
                        e.getMessage();
                    }
                }
            };

            MyLocation myLocation = new MyLocation();
            myLocation.getLocation(this, locationResult);
        } else {
            // showSettingsAlert("NETWORK");
        }
    }

    public String GetAddress(double latitude, double longitude) {

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        String city = "", state = "", address = "";
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            Log.d("Addrss", addresses + "");
            // latlong = new LatLng(latitude, longitude);
            // address = addresses.get(0).getAddressLine(0) + " " + addresses.get(0).getAddressLine(1) + " " + addresses.get(0).getAddressLine(2);
            address = addresses.get(0).getAddressLine(0);
            city = addresses.get(0).getAddressLine(1);
            state = addresses.get(0).getAdminArea();
            String zip = addresses.get(0).getPostalCode();
            String country = addresses.get(0).getCountryName();
        } catch (Exception e) {

        }
        return address;
    }

    private void postAttandance() {
        LoginModel model = sh.getLoginModel("LOGIN_MODEL");
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
        final String createddate = df.format(Calendar.getInstance().getTime());

        if (status.equalsIgnoreCase("signin")) {
            status = "signout";
            signin.setBackground(ContextCompat.getDrawable(AttandanceActivity.this, R.drawable.ic_signout));
            textViewsignin.setText("SIGN IN");
        } else {
            status = "signin";
            signin.setBackground(ContextCompat.getDrawable(AttandanceActivity.this, R.drawable.ic_signin));
            textViewsignin.setText("SIGN OUT");
        }

        Singleton.getInstance().getApi().postAttendance(model.getId(), currentLocation, createddate, status).enqueue(new Callback<ResMetaMeeting>() {
            @Override
            public void onResponse(Call<ResMetaMeeting> call, Response<ResMetaMeeting> response) {


            }

            @Override
            public void onFailure(Call<ResMetaMeeting> call, Throwable t) {

            }
        });

    }

    private void getAttandanceStatus() {
        LoginModel model = sh.getLoginModel("LOGIN_MODEL");

        Singleton.getInstance().getApi().getAttandanceStatus(model.getId()).enqueue(new Callback<ResAttandance>() {
            @Override
            public void onResponse(Call<ResAttandance> call, Response<ResAttandance> response) {

                ArrayList<AttandenceModel> model = response.body().getResponse();
                if (model.size() > 0)
                    status = model.get(0).getStatus();

                if (status.equalsIgnoreCase("signin")) {
                    signin.setBackground(ContextCompat.getDrawable(AttandanceActivity.this, R.drawable.ic_signout));
                    textViewsignin.setText("SIGN OUT");
                } else {
                    signin.setBackground(ContextCompat.getDrawable(AttandanceActivity.this, R.drawable.ic_signin));
                    textViewsignin.setText("SIGN IN");
                }
                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResAttandance> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });

    }

}