package com.mmcs.trackapp.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mmcs.trackapp.R;
import com.mmcs.trackapp.fragment.FragmentHome;
import com.mmcs.trackapp.util.Shprefrences;

import static com.mmcs.trackapp.activity.LandingActivity.MY_PERMISSIONS_REQUEST_LOCATION;

/**
 * Created by aphroecs on 10/10/2016.
 */
public class DrawerActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    RelativeLayout drawerIcon;
    public static boolean isHome = true;
    public static FragmentManager fragmentManager;
    TextView txt_meeting, txt_myschedule, txt_feedback, txt_client, txt_attendance, txt_expense, txt_setting, txt_pending, txt_message, txt_logout;
    public static LandingActivity activity;
    Shprefrences sh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT > 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
       // activity=this;
        setContentView(R.layout.landing_activity);
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

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
        }

        sh = new Shprefrences(this);
        fragmentManager = getSupportFragmentManager();
        pushFragment(new FragmentHome());
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        drawerIcon = (RelativeLayout) findViewById(R.id.drawerIcon);
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
                Intent intent = new Intent(DrawerActivity.this, FeedbackActivity.class);
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
                Intent intent = new Intent(DrawerActivity.this, SendMessageActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(Gravity.LEFT);
            }
        });
        txt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sh.clearData();
                Toast.makeText(DrawerActivity.this, "You have logged out successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DrawerActivity.this, LoginActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(Gravity.LEFT);
            }
        });















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


}
