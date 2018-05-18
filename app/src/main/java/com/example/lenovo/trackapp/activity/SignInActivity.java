package com.example.lenovo.trackapp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.trackapp.db.AttandanceDB;
import com.example.lenovo.trackapp.R;

import java.util.Calendar;

public class SignInActivity extends AppCompatActivity implements LocationListener {

    TextView location1,date;
    TextView time;
    Button submit;
    int H,M;
    int DD,MM,YY;
    Calendar calendar;
    LocationManager locationManager;
    private boolean firstTimeUsed = false;
    private String firstTimeUsedKey="FIRST_TIME";
    private String sharedPreferencesKey = "MY_PREF";
    private String buttonClickedKey = "BUTTON_CLICKED";
    private SharedPreferences mPrefs;
    private long savedDate=0;
   // Location location=getCurrentLocation();
    AttandanceDB database=new AttandanceDB(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        location1=(TextView)findViewById(R.id.btn_location);
        date=(TextView)findViewById(R.id.btn_date);
        time=(TextView)findViewById(R.id.btn_time);
        submit=(Button)findViewById(R.id.btn_submit);
        mPrefs = getSharedPreferences(sharedPreferencesKey, Context.MODE_PRIVATE);
        savedDate = mPrefs.getLong(buttonClickedKey,0);
        firstTimeUsed = mPrefs.getBoolean(firstTimeUsedKey,true);//default is true if no value is saved
        checkPrefs();
        calendar=Calendar.getInstance();
        DD=calendar.get(Calendar.DAY_OF_MONTH);
        MM=calendar.get(Calendar.MONTH);
        YY=calendar.get(Calendar.YEAR);
        locationManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
        date.setText("DATE:"+String.valueOf(DD)+"-"+String.valueOf(MM+1)+"-"+String.valueOf(YY));
        H = calendar.get(Calendar.HOUR_OF_DAY);
        M = calendar.get(Calendar.MINUTE);

        if (H < 12 && H >= 0) {
            time.setText("IN TIME:"+String.valueOf(H)+":"+String.valueOf(M)+" "+"AM");
        } else {
            H -= 12;
            if(H == 0) {
                H= 12;
            }
            time.setText("IN TIME:"+String.valueOf(H)+":"+String.valueOf(M)+" "+"PM");
        }
        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                String loc=location1.getText().toString();
                String d=date.getText().toString();
                String t=time.getText().toString();

                database.saveData(loc,d,t);
                Toast.makeText(SignInActivity.this, "saved", Toast.LENGTH_LONG).show();
                saveClickedTime();

            }
        });


    }
    private void checkPrefs(){


        if(firstTimeUsed==false){
            if(savedDate>0){

                //create two instances of Calendar and set minute,hour,second and millis
                //to 1, to be sure that the date differs only from day,month and year
                Calendar currentCal = Calendar.getInstance();
                currentCal.set(Calendar.MINUTE,1);
                currentCal.set(Calendar.HOUR,1);
                currentCal.set(Calendar.SECOND,1);
                currentCal.set(Calendar.MILLISECOND,1);
                Calendar savedCal = Calendar.getInstance();
                savedCal.setTimeInMillis(savedDate); //set the time in millis from saved in sharedPrefs
                savedCal.set(Calendar.MINUTE,1);
                savedCal.set(Calendar.HOUR,1);
                savedCal.set(Calendar.SECOND,1);
                savedCal.set(Calendar.MILLISECOND,1);
                if(currentCal.getTime().after(savedCal.getTime())){
                    submit.setVisibility(View.VISIBLE);
                }
                else if(currentCal.getTime().equals(savedCal.getTime())){
                    Toast.makeText(SignInActivity.this, "Already Signin,submit button is disable Now", Toast.LENGTH_SHORT).show();

                    submit.setEnabled(false);
                }
            }
        }else{

            //just set the button visible if app is used the first time
            submit.setVisibility(View.VISIBLE);
        }
    }
    private void saveClickedTime(){
        SharedPreferences.Editor mEditor = mPrefs.edit();
        Calendar cal = Calendar.getInstance();
        long millis = cal.getTimeInMillis();
        mEditor.putLong(buttonClickedKey,millis);
        mEditor.putBoolean(firstTimeUsedKey,false); //the button is clicked first time, so set the boolean to false.
        mEditor.commit();

        //hide the button after clicked
        submit.setEnabled(false);

    }

   /* public String getCompleteAddressString() {
        Location location=getCurrentLocation();
        double longitude=location.getLongitude();
        double latitude=location.getLatitude();

        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {


            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();

                Log.w("Current loction address", strReturnedAddress.toString());
            }







          else {
                Log.w("Current loction address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("Current loction address", "Canont get Address!");
        }
        return strAdd;
    }


    public Location getCurrentLocation() {
        // TODO Auto-generated method stub
        try{
            Location location=null;

            if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
            {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,10,10,this);
                location= locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                return location;
            }
        }catch(Exception e){
            e.printStackTrace();
            Log.w("Current loction address", "Canont get Address!");


        }
        return location;



    }*/

    @Override
    public void onLocationChanged(Location arg0) {
        // TODO Auto-generated method stub

    }
    @Override
    public void onProviderDisabled(String arg0) {
        // TODO Auto-generated method stub

    }
    @Override
    public void onProviderEnabled(String arg0) {
        // TODO Auto-generated method stub

    }
    @Override
    public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
        // TODO Auto-generated method stub

    }


    public static class Attandance extends AppCompatActivity {

        Button signin,signout ,checkhistory;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_attandance);
            signin=(Button)findViewById(R.id.btn_singin);
            signout=(Button)findViewById(R.id.btn_singout);
            checkhistory=(Button)findViewById(R.id.btn_history);


            signout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    Intent intent=new Intent(Attandance.this,SignOutActivity.class);
                    startActivity(intent);

                }
            });
            signin.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub



                    Intent intent=new Intent(Attandance.this,SignInActivity.class);
                    startActivity(intent);

                }
            });

            checkhistory.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    Intent intent=new Intent(Attandance.this,AttandanceHisActivity.class);
                    startActivity(intent);
                    finish();

                }



            });




        }


    }
}
