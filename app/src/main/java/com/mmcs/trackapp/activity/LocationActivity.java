package com.mmcs.trackapp.activity;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mmcs.trackapp.R;
import com.mmcs.trackapp.model.MeetingModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aphroecs on 9/16/2016.
 */
public class LocationActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    public static ArrayList<MeetingModel> list = new ArrayList<>();

    double lat = 28.621272;
    double lng = 77.061327;

    public static double mylat = 28.621272;
    public static double mylng = 77.061327;
    ImageView imgLogo;
    MeetingModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_activity);
        model = (MeetingModel) getIntent().getSerializableExtra("MEETINGMODEL");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        back();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney, Australia, and move the camera.
        UiSettings mUiSettings = mMap.getUiSettings();
        mUiSettings.setZoomControlsEnabled(false);
        //mMap.setMyLocationEnabled(true);
        mUiSettings.setMyLocationButtonEnabled(true);
        mUiSettings.setTiltGesturesEnabled(true);
        mUiSettings.setRotateGesturesEnabled(false);
        mUiSettings.setMapToolbarEnabled(true);
        // mMap.setOnCameraChangeListener(this);

      LatLng latlng=  LocationFromAddress(this,model.getAddress());

        Marker mark = mMap.addMarker(new MarkerOptions().position(latlng).title(model.getDescreption()));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 10.0f));
        mMap.setOnInfoWindowClickListener(this);
    }


    @Override
    public boolean onMarkerClick(final Marker marker) {

        return false;
    }

    private void back() {
        RelativeLayout drawerIcon = (RelativeLayout) findViewById(R.id.drawerIcon);
        drawerIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    public void onInfoWindowClick(Marker marker) {

        marker.showInfoWindow();
        /*for (int i = 0; i < list.size(); i++) {

            if (marker.getTitle().equalsIgnoreCase(list.get(i).getArea_name().trim())) {
                FragmentHome.placeId = list.get(i).getId().trim();
                FragmentHome.isLoadLocation = true;
                FragmentHome.address = list.get(i).getArea_name().trim();
                finish();
            }
        }*/
    }


    LatLng  LocationFromAddress(Context context, String strAddress)
    {
        Geocoder coder= new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try
        {
            address = coder.getFromLocationName(strAddress, 5);
            if(address==null)
            {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return p1;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}