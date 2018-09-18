package com.mmcs.trackapp.activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mmcs.trackapp.R;
import com.mmcs.trackapp.adaptor.HomeRecyclerAdaptor;
import com.mmcs.trackapp.adaptor.SideBarAdaptor;
import com.mmcs.trackapp.fragment.FragmentHome;
import com.mmcs.trackapp.model.HomeItemModel;
import com.mmcs.trackapp.model.HomeItemResMeta;
import com.mmcs.trackapp.model.LoginModel;
import com.mmcs.trackapp.model.PortResMeta;
import com.mmcs.trackapp.model.PreRequestResMeta;
import com.mmcs.trackapp.util.AppLocationService;
import com.mmcs.trackapp.util.CircleTransform;
import com.mmcs.trackapp.util.MyLocation;
import com.mmcs.trackapp.util.Shprefrences;
import com.mmcs.trackapp.util.Singleton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

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
    TextView txtName, txtEmail, txtDepartment, txt_meeting, txt_myschedule, txt_feedback, txt_client, txt_attendance, txt_expense, txt_setting, txt_pending, txt_message, txt_logout;
    Shprefrences sh;
    LoginModel model;
    ImageView imgProfile;
    ListView list;
    public static ArrayList<HomeItemModel> listHomeItems;
    public static HomeRecyclerAdaptor homeAdaptor;
    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;
    private Snackbar snackbar;
    RelativeLayout relativeLayout;
    private boolean internetConnected=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listHomeItems = new ArrayList<>();
        fragmentManager = getSupportFragmentManager();
        getMenu();
        if (Build.VERSION.SDK_INT > 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.landing_activity);
        list = findViewById(R.id.listItem);
        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
        sh = new Shprefrences(this);
        txtName = (TextView) findViewById(R.id.txtName);
        txtEmail = (TextView) findViewById(R.id.txtEmail);
        relativeLayout=findViewById(R.id.relativeLayout);
        txtDepartment = (TextView) findViewById(R.id.txtDepartment);
        imgProfile = findViewById(R.id.imgProfile);
        model = sh.getLoginModel(getString(R.string.login_model));
        if (model.getImage() != null)
            Picasso.get().load(model.getImage()).transform(new CircleTransform()).placeholder(R.drawable.ic_userlogin).resize(100, 100).into(imgProfile);
        txtName.setText(model.getDisplay_name());
        setTitle();
       // pushFragment(new FragmentHome());
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
        registerInternetCheckReceiver();
        model = sh.getLoginModel(getString(R.string.login_model));
        if (model != null) {
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

    private void updateLocation(String lat, String lng) {
        Singleton.getInstance().getApi().postDeviceLocation(model.getId(), lat, lng).enqueue(new Callback<PreRequestResMeta>() {
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
                            double lat = location.getLatitude();
                            double lon = location.getLongitude();
                            updateLocation(lat + "", "" + lon);
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


    public void getMenu() {
        Singleton.getInstance().getApi().getMenu("").enqueue(new Callback<HomeItemResMeta>() {
            @Override
            public void onResponse(Call<HomeItemResMeta> call, Response<HomeItemResMeta> response) {

                listHomeItems = response.body().getResponse();
                //setMenuItemIcons();
                HomeItemModel logout=new HomeItemModel();
                logout.setTitle("Logout");
                listHomeItems.add(logout);
                SideBarAdaptor adaptor = new SideBarAdaptor(DrawerActivity.this, listHomeItems);
                homeAdaptor= new HomeRecyclerAdaptor(DrawerActivity.this, listHomeItems);
                list.setAdapter(adaptor);
                pushFragment(new FragmentHome());
            }

            @Override
            public void onFailure(Call<HomeItemResMeta> call, Throwable t) {
                Log.e("except", "**exception****" + t.getMessage());
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }
    /**
     *  Method to register runtime broadcast receiver to show snackbar alert for internet connection..
     */
    private void registerInternetCheckReceiver() {
        IntentFilter internetFilter = new IntentFilter();
        internetFilter.addAction("android.net.wifi.STATE_CHANGE");
        internetFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(broadcastReceiver, internetFilter);
    }
    /**
     *  Runtime Broadcast receiver inner class to capture internet connectivity events
     */
    public BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String status = getConnectivityStatusString(context);
            setSnackbarMessage(status,false);
        }
    };
    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;

            if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }
    public static String getConnectivityStatusString(Context context) {
        int conn = getConnectivityStatus(context);
        String status = null;
        if (conn == TYPE_WIFI) {
            status = "Wifi enabled";
        } else if (conn == TYPE_MOBILE) {
            status = "Mobile data enabled";
        } else if (conn == TYPE_NOT_CONNECTED) {
            status = "Not connected to Internet";
        }
        return status;
    }
    private void setSnackbarMessage(String status,boolean showBar) {
        String internetStatus="";
        if(status.equalsIgnoreCase("Wifi enabled")||status.equalsIgnoreCase("Mobile data enabled")){
            internetStatus="Internet Connected";
        }else {
            internetStatus="Check Internet Connection";
        }
        snackbar = Snackbar
                .make(relativeLayout, internetStatus, Snackbar.LENGTH_LONG)
                .setAction("Refresh", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                        startActivity(getIntent());
                    }
                });
        snackbar.setActionTextColor(Color.WHITE);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        if(internetStatus.equalsIgnoreCase("Check Internet Connection")){
            if(internetConnected){
                snackbar.show();
                internetConnected=false;
            }
        }else{
            if(!internetConnected){
                internetConnected=true;
                snackbar.show();
            }
        }
    }



}
