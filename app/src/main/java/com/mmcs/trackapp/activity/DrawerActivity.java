package com.mmcs.trackapp.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mmcs.trackapp.R;
import com.mmcs.trackapp.fragment.FragmentHome;
import com.mmcs.trackapp.model.LoginModel;
import com.mmcs.trackapp.model.PreRequestResMeta;
import com.mmcs.trackapp.util.AppLocationService;
import com.mmcs.trackapp.util.CircleTransform;
import com.mmcs.trackapp.util.MyLocation;
import com.mmcs.trackapp.util.Shprefrences;
import com.mmcs.trackapp.util.Singleton;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by aphroecs on 10/10/2016.
 */
public class DrawerActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    RelativeLayout drawerIcon;
    public static boolean isHome = true;
    public static FragmentManager fragmentManager;
    TextView txtName,txtEmail,txtDepartment, txt_meeting, txt_myschedule, txt_feedback, txt_client, txt_attendance, txt_expense, txt_setting, txt_pending, txt_message, txt_logout;
    Shprefrences sh;
    LoginModel model;
    ImageView imgProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT > 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.landing_activity);
        if(!hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }

        sh = new Shprefrences(this);
        txt_meeting = (TextView) findViewById(R.id.txt_meeting);
        txt_myschedule = (TextView) findViewById(R.id.txt_myschedule);
        txt_feedback = (TextView) findViewById(R.id.txt_feedback);
        txt_client = (TextView) findViewById(R.id.txt_client);
        txt_attendance = (TextView) findViewById(R.id.txt_attendance);
        txt_expense = (TextView) findViewById(R.id.txt_expense);
        txt_setting = (TextView) findViewById(R.id.txt_setting);
        txt_pending = (TextView) findViewById(R.id.txt_pending);
        txt_message = (TextView) findViewById(R.id.txt_message);
        txt_logout = (TextView) findViewById(R.id.txt_logout);
        txtName = (TextView) findViewById(R.id.txtName);
        txtEmail = (TextView) findViewById(R.id.txtEmail);
        txtDepartment = (TextView) findViewById(R.id.txtDepartment);

        imgProfile = findViewById(R.id.imgProfile);
        model = sh.getLoginModel(getString(R.string.login_model));
        if(model.getImage()!=null)
       //Picasso.get().load(model.getImage()).transform(new CircleTransform()).placeholder(R.drawable.ic_userlogin).resize(100,100).into(imgProfile);
        txtName.setText(model.getDisplay_name());
        setTitle();

        fragmentManager = getSupportFragmentManager();
        pushFragment(new FragmentHome());
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        drawerIcon = (RelativeLayout) findViewById(R.id.drawerIcon);
        ImageView imgDrawer = findViewById(R.id.imgDrawer);
        imgDrawer.setBackground(ContextCompat.getDrawable(DrawerActivity.this, R.drawable.ic_menu));
        drawerIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (drawerLayout.isDrawerOpen(Gravity.LEFT))
                    drawerLayout.closeDrawer(Gravity.LEFT);
                else
                    drawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        txt_meeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DrawerActivity.this, CreateMeetingActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(Gravity.LEFT);
                drawerLayout.closeDrawer(Gravity.LEFT);
            }
        });
        txt_myschedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DrawerActivity.this, MyScheduleActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(Gravity.LEFT);
            }
        });
        txt_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DrawerActivity.this, FeedbackListActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(Gravity.LEFT);
            }
        });
        txt_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DrawerActivity.this, AddCustomerActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(Gravity.LEFT);
            }
        });
        txt_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DrawerActivity.this, AttandanceActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(Gravity.LEFT);
            }
        });
        txt_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DrawerActivity.this, ExpenseActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(Gravity.LEFT);
            }
        });
        txt_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DrawerActivity.this, SettingActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(Gravity.LEFT);
            }
        });
        txt_pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DrawerActivity.this, PendingActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(Gravity.LEFT);
            }
        });
        txt_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DrawerActivity.this, MessageActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(Gravity.LEFT);
            }
        });
        txt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sh.clearData();
                Toast.makeText(DrawerActivity.this, getString(R.string.you_have_logged_out_successfully), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DrawerActivity.this, LoginActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(Gravity.LEFT);
            }
        });

        getLocation();

    }

    public static void pushFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.frame_container, fragment);
            transaction.commit();
        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    private void setTitle() {
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(getString(R.string.bTrack));
    }

    @Override
    protected void onResume() {
        super.onResume();
        model = sh.getLoginModel(getString(R.string.login_model));
        if(model!=null) {
            Picasso.get().load(model.getImage()).transform(new CircleTransform()).placeholder(R.drawable.ic_userlogin).into(imgProfile);
            txtName.setText(model.getDisplay_name());
            txtEmail.setText(model.getEmail());
            txtDepartment.setText("(" + model.getDepartment() + ")");
        }
    }

    //*********************************check permission*************************

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA
    };

    private void updateLocation(String lat,String lng)
    {
        Singleton.getInstance().getApi().postDeviceLocation(model.getId(),lat,lng).enqueue(new Callback<PreRequestResMeta>() {
            @Override
            public void onResponse(Call<PreRequestResMeta> call, Response<PreRequestResMeta> response) {

            }

            @Override
            public void onFailure(Call<PreRequestResMeta> call, Throwable t) {

            }
        });
    }

    AppLocationService appLocationService;
    Location nwLocation;

    public void getLocation() {
        appLocationService = new AppLocationService(DrawerActivity.this);
        nwLocation = appLocationService.getLocation(LocationManager.NETWORK_PROVIDER);
        if (nwLocation != null) {
            MyLocation.LocationResult locationResult = new MyLocation.LocationResult() {
                @Override
                public void gotLocation(Location location) {
                    //Got the location
                    try {
                        if (location != null) {
                            double lat= location.getLatitude();
                            double lon = location.getLongitude();
                            updateLocation(lat+"",""+lon);
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


}
