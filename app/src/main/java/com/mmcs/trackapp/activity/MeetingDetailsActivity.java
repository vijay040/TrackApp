package com.mmcs.trackapp.activity;

import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mmcs.trackapp.R;
import com.mmcs.trackapp.model.LoginModel;
import com.mmcs.trackapp.model.MeetingModel;
import com.mmcs.trackapp.model.PreRequestResMeta;
import com.mmcs.trackapp.model.ResMetaMeeting;
import com.mmcs.trackapp.util.AppLocationService;
import com.mmcs.trackapp.util.MyLocation;
import com.mmcs.trackapp.util.Shprefrences;
import com.mmcs.trackapp.util.Singleton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MeetingDetailsActivity extends AppCompatActivity {

    TextView descreption, purpose, customer, agenda, date, time, address, contact;
    MeetingModel model;
    Shprefrences sh;
    ImageView start, imgMap;
    public static String currentLocation;
    public String status = "";
    ProgressBar progressbar;
    TextView txt_meetingstatus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        sh = new Shprefrences(this);
        setContentView(R.layout.acitivity_meetingdetails);
        model = (MeetingModel) getIntent().getSerializableExtra(getString(R.string.meeting_model));
        progressbar = findViewById(R.id.progressbar);
        progressbar.setVisibility(View.VISIBLE);
        getMeetingStatus();
        start = findViewById(R.id.start);
        imgMap = findViewById(R.id.imgMap);
        descreption = findViewById(R.id.txtdescreption);
        purpose = findViewById(R.id.txtpurpose);
        customer = findViewById(R.id.txtcustomer);
        agenda = findViewById(R.id.txtagenda);
        date = findViewById(R.id.txtdate);
        time = findViewById(R.id.txttime);
        address = findViewById(R.id.txtaddress);
        contact = findViewById(R.id.txtcontactperson);
        txt_meetingstatus = findViewById(R.id.txt_meetingstatus);
        back();
        setTitle();
        descreption.setText(getString(R.string.description) + model.getDescreption());
        purpose.setText(getString(R.string.purpose) + model.getPurpose());
        customer.setText(getString(R.string.client) + model.getCustomer_name());
        agenda.setText(getString(R.string.agenda) + model.getAgenda());
        date.setText(getString(R.string.date) + model.getDate());
        time.setText(getString(R.string.time) + model.getTime());
        address.setText(getString(R.string.address) + model.getAddress());
        contact.setText(getString(R.string.contact_person) + model.getContact_person());
        //getSupportActionBar().setTitle("Meeting Detail");
        SpannableStringBuilder sb = new SpannableStringBuilder(purpose.getText());
        // Span to set text color to some RGB value
        ForegroundColorSpan fcs = new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary));
        // Span to make text bold
        //    final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD);
        // Set the text color for first 4 characters
        sb.setSpan(fcs, 0, 8, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

// make them also bold
        // sb.setSpan(bss, 0, 8, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        purpose.setText(sb);
        sb = new SpannableStringBuilder(descreption.getText());
        fcs = new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary));
        sb.setSpan(fcs, 0, 12, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        descreption.setText(sb);

        sb = new SpannableStringBuilder(customer.getText());
        fcs = new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary));
        sb.setSpan(fcs, 0, 7, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        customer.setText(sb);

        sb = new SpannableStringBuilder(agenda.getText());
        fcs = new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary));
        sb.setSpan(fcs, 0, 7, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        agenda.setText(sb);

        sb = new SpannableStringBuilder(date.getText());
        fcs = new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary));
        sb.setSpan(fcs, 0, 5, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        date.setText(sb);

        sb = new SpannableStringBuilder(time.getText());
        fcs = new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary));
        sb.setSpan(fcs, 0, 5, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        time.setText(sb);

        sb = new SpannableStringBuilder(address.getText());
        fcs = new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary));
        sb.setSpan(fcs, 0, 8, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        address.setText(sb);

        sb = new SpannableStringBuilder(contact.getText());
        fcs = new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary));
        sb.setSpan(fcs, 0, 15, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        contact.setText(sb);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressbar.setVisibility(View.VISIBLE);
                chkUpdate();
            }
        });
        imgMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MeetingDetailsActivity.this, LocationActivity.class);
                intent.putExtra(getString(R.string.meeting_model), model);
                startActivity(intent);
            }
        });
        getLocation();

    }

    String followup, mom;

    private void chkUpdate() {
        DateFormat dateFormat = new SimpleDateFormat(getString(R.string.formate_date));
        Date date = new Date();
        String datetime = dateFormat.format(date);
        String startTime = "", endTime = "", startAddress = "", endAddress = "";
        String currentStatus = status;
        if (status.equalsIgnoreCase(getString(R.string.s_start))) {
            endTime = datetime;
            endAddress = currentLocation;
            currentStatus = getString(R.string.end);
            startActivityForResult(new Intent(MeetingDetailsActivity.this, FollowupActivity.class), 200);
        } else {
            startTime = datetime;
            startAddress = currentLocation;
            currentStatus = getString(R.string.start);
            updateMeetingStatus(startTime, endTime, currentStatus, startAddress, endAddress);
        }

    }

    private void update() {
        DateFormat dateFormat = new SimpleDateFormat(getString(R.string.formate_date));
        Date date = new Date();
        String datetime = dateFormat.format(date);
        String startTime = "", endTime = "", startAddress = "", endAddress = "";
        String currentStatus = status;
        if (status.equalsIgnoreCase(getString(R.string.s_start))) {
            endTime = datetime;
            endAddress = currentLocation;
            currentStatus = getString(R.string.end);
        } else {
            startTime = datetime;
            startAddress = currentLocation;
            currentStatus = getString(R.string.start);
        }
        updateMeetingStatus(startTime, endTime, currentStatus, startAddress, endAddress);
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

    private void setTitle() {
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(getString(R.string.meeting_details));
    }


    AppLocationService appLocationService;
    Location nwLocation;

    public void getLocation() {
        appLocationService = new AppLocationService(MeetingDetailsActivity.this);
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

    String indate,intime;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == RESULT_OK) {
            followup = (String) data.getExtras().get("NEED_FOLLOWUP");
            mom = (String) data.getExtras().get("MOM");
            indate = (String) data.getExtras().get("DATE");
            intime = (String) data.getExtras().get("TIME");
            update();
            finish();
        } else
            progressbar.setVisibility(View.GONE);
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

    public void updateMeetingStatus(String startTime, String endTime, final String status, String startAddress, String endAddress) {
        LoginModel mo = sh.getLoginModel(getString(R.string.login_model));
        Singleton.getInstance().getApi().updateMeedingStatus(mo.getId(), startTime, endTime, status, model.getId(), startAddress, endAddress, followup, mom,indate,intime).enqueue(new Callback<PreRequestResMeta>() {
            @Override
            public void onResponse(Call<PreRequestResMeta> call, Response<PreRequestResMeta> response) {
                MeetingDetailsActivity.this.status = status;
                if (status.equalsIgnoreCase(getString(R.string.s_start))) {
                    start.setBackground(ContextCompat.getDrawable(MeetingDetailsActivity.this, R.drawable.ic_stop));
                    txt_meetingstatus.setText(getString(R.string.EndMeeting));
                } else {
                    start.setBackground(ContextCompat.getDrawable(MeetingDetailsActivity.this, R.drawable.ic_start));
                    txt_meetingstatus.setText(getString(R.string.StartMeeting));
                }
                progressbar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<PreRequestResMeta> call, Throwable t) {
                progressbar.setVisibility(View.GONE);
            }
        });
    }

    public void getMeetingStatus() {
        LoginModel mo = sh.getLoginModel(getString(R.string.login_model));
        Singleton.getInstance().getApi().getMeetingStatus(mo.getId(), model.getId()).enqueue(new Callback<ResMetaMeeting>() {
            @Override
            public void onResponse(Call<ResMetaMeeting> call, Response<ResMetaMeeting> response) {

                MeetingModel meetingData = response.body().getResponse().get(0);
                if (meetingData != null) {
                    status = meetingData.getStatus();
                    if (status.equalsIgnoreCase(getString(R.string.s_start))) {
                        start.setBackground(ContextCompat.getDrawable(MeetingDetailsActivity.this, R.drawable.ic_stop));
                        txt_meetingstatus.setText(getString(R.string.EndMeeting));
                    } else {
                        start.setBackground(ContextCompat.getDrawable(MeetingDetailsActivity.this, R.drawable.ic_start));
                        txt_meetingstatus.setText(getString(R.string.StartMeeting));
                    }


                    progressbar.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<ResMetaMeeting> call, Throwable t) {
                progressbar.setVisibility(View.GONE);
            }
        });
    }
}
