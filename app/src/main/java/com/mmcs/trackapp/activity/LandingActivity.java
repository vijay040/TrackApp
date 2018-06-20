package com.mmcs.trackapp.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mmcs.trackapp.R;
import com.mmcs.trackapp.model.LoginModel;
import com.mmcs.trackapp.model.PreRequestResMeta;
import com.mmcs.trackapp.model.ResAttandance;
import com.mmcs.trackapp.util.AppLocationService;
import com.mmcs.trackapp.util.MyLocation;
import com.mmcs.trackapp.util.Shprefrences;
import com.mmcs.trackapp.util.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LandingActivity extends AppCompatActivity {
    Button addvisit, expenses, attandance, schedule, addcustomer, feedback, pending, message, setting;
    String name = "";
    Shprefrences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addvisit = (Button) findViewById(R.id.btn_addvisit);
        expenses = (Button) findViewById(R.id.btn_expense);
        attandance = (Button) findViewById(R.id.btn_attendance);
        schedule = (Button) findViewById(R.id.btn_mysch);
        message = (Button) findViewById(R.id.btn_message);
        feedback = (Button) findViewById(R.id.btn_feedback);
        addcustomer = (Button) findViewById(R.id.btn_addcustomer);
        pending = (Button) findViewById(R.id.btn_notification);
        message = (Button) findViewById(R.id.btn_message);
        setting = (Button) findViewById(R.id.btn_setting);
        TextView txtWelcomeText = findViewById(R.id.txtWelcomeText);
        sh = new Shprefrences(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
        }
        LoginModel model = sh.getLoginModel("LOGIN_MODEL");
        if (model != null)
            name = model.getDisplay_name();
        txtWelcomeText.setText("Hi " + name + "! B Tracker Welcomes You.");
        // getSupportActionBar().setTitle("B Tracker");
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LandingActivity.this,SettingActivity.class));
            }
        });
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LandingActivity.this, SendMessageActivity.class);
                startActivity(intent);
            }
        });

        pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //sh.clearData();
                Intent intent = new Intent(LandingActivity.this, PendingActivity.class);
                startActivity(intent);

            }
        });
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LandingActivity.this, FeedbackActivity.class);
                startActivity(intent);
            }
        });
        addcustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LandingActivity.this, AddCustomerActivity.class);
                startActivity(intent);
            }
        });

        schedule.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(LandingActivity.this, MyScheduleActivity.class);
                startActivity(intent);
            }
        });
        attandance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(LandingActivity.this, AttandanceActivity.class);
                startActivity(intent);
            }
        });
        addvisit.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(LandingActivity.this, CreateMeetingActivity.class);
                startActivity(intent);
            }
        });
        expenses.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(LandingActivity.this, ExpenseActivity.class);
                startActivity(intent);
            }
        });


        test();
    }

    public void test()
    {
        Singleton.getInstance().getApi().test("16").enqueue(new Callback<ResAttandance>() {
            @Override
            public void onResponse(Call<ResAttandance> call, Response<ResAttandance> response) {

            }

            @Override
            public void onFailure(Call<ResAttandance> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getLocation();
    }

    AppLocationService appLocationService;
    Location nwLocation;
    public void getLocation() {
        appLocationService = new AppLocationService(LandingActivity.this);
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
                            postDeviceLocation(latitude+"",""+longitude);
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

    public void  postDeviceLocation(String latitude,String longitude)
    {
        LoginModel model = sh.getLoginModel("LOGIN_MODEL");
        Singleton.getInstance().getApi().postDeviceLocation(model.getId(),latitude,longitude).enqueue(new Callback<PreRequestResMeta>() {
            @Override
            public void onResponse(Call<PreRequestResMeta> call, Response<PreRequestResMeta> response) {

            }

            @Override
            public void onFailure(Call<PreRequestResMeta> call, Throwable t) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        int itemid = item.getItemId();
        if (itemid == R.id.nav_addmeeting) {
            Intent intent = new Intent(LandingActivity.this, CreateMeetingActivity.class);
            startActivity(intent);
        } else if (itemid == R.id.nav_mysch) {
            Intent intent = new Intent(LandingActivity.this, MyScheduleActivity.class);
            startActivity(intent);
        } else if (itemid == R.id.nav_feedback) {
            Intent intent = new Intent(LandingActivity.this, FeedbackActivity.class);
            startActivity(intent);
        } else if (itemid == R.id.nav_attandance) {
            startActivity(new Intent(LandingActivity.this,AttandanceActivity.class));
        } else if (itemid == R.id.nav_customer) {
            Intent intent = new Intent(LandingActivity.this, AddCustomerActivity.class);
            startActivity(intent);
        } else if (itemid == R.id.nav_expense) {
            Intent intent = new Intent(LandingActivity.this, ExpenseActivity.class);
            startActivity(intent);
        } else if (itemid == R.id.nav_setting) {
         startActivity(new Intent(LandingActivity.this,SettingActivity.class));
        } else if (itemid == R.id.nav_message) {
            Intent intent = new Intent(LandingActivity.this, SendMessageActivity.class);
            startActivity(intent);
        } else if (itemid == R.id.nav_notification) {
         startActivity(new Intent(LandingActivity.this,PendingActivity.class));
        } else if (itemid == R.id.nav_logout) {
            sh.clearData();
            Toast.makeText(this, "You have logged out successfully!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LandingActivity.this, LoginActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

    }
}
