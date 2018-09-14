package com.mmcs.trackapp.activity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.mmcs.trackapp.R;
import com.mmcs.trackapp.adaptor.PlaceArrayAdapter;
import com.mmcs.trackapp.model.LoginModel;
import com.mmcs.trackapp.model.ResMetaMeeting;
import com.mmcs.trackapp.util.Shprefrences;
import com.mmcs.trackapp.util.Singleton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class NewCustomerActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks {
    EditText customername,email,phone,pin,companyname,country,taxdetails;
    Button submit;
    TextView save;
    private AutoCompleteTextView address;
    private static final String TAG = "NewCustomerActivity";
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private GoogleApiClient mGoogleApiClient;
    private PlaceArrayAdapter mPlaceArrayAdapter;
    Shprefrences sh;
    ProgressBar progress;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        sh=new Shprefrences(this);
        setContentView(R.layout.activity_new_customer);
        customername=(EditText)findViewById(R.id.edt_name);
        address=(AutoCompleteTextView)findViewById(R.id.edt_address);
        email=(EditText)findViewById(R.id.edt_email);
        phone=(EditText)findViewById(R.id.edt_phone);
        pin=findViewById(R.id.edt_pin);
        companyname=findViewById(R.id.edt_company);
        progress = findViewById(R.id.progress);
        country=findViewById(R.id.edt_country);
        taxdetails=findViewById(R.id.edt_taxdetails);
        submit=findViewById(R.id.btnSubmit);
        back();
        setTitle();
        //getSupportActionBar().setTitle("New Customer");
        mGoogleApiClient = new GoogleApiClient.Builder(NewCustomerActivity.this)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, GOOGLE_API_CLIENT_ID, this)
                .addConnectionCallbacks(this)
                .build();
        address.setOnItemClickListener(mAutocompleteClickListener);
        mPlaceArrayAdapter = new PlaceArrayAdapter(
                this, android.R.layout.simple_list_item_1,
                BOUNDS_MOUNTAIN_VIEW, null);
        address.setAdapter(mPlaceArrayAdapter);
        address.setThreshold(1);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String custmrname=customername.getText().toString();
                String add=address.getText().toString();
                String em=email.getText().toString();
                String phn=phone.getText().toString();
                String Pin=pin.getText().toString();
                String Company=companyname.getText().toString();
                String Country=country.getText().toString();
                String Tax=taxdetails.getText().toString();
                if(custmrname.equals("")){
                    Toast.makeText(NewCustomerActivity.this,getString(R.string.enter_customer_name),Toast.LENGTH_SHORT).show();
                }
                else if(add.equals("")){
                    Toast.makeText(NewCustomerActivity.this,getString(R.string.enter_address),Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(em.trim().isEmpty()||!em.matches("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                        "\\@" +
                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                        "(" +
                        "\\." +
                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                        ")+")) {

                    Toast.makeText(NewCustomerActivity.this,getString(R.string.enter_valid_email),Toast.LENGTH_SHORT).show();
                 return;
                }
                else if(phn.trim().isEmpty()||phone.getText().toString().length()<10||phn.length()>12){
                    Toast.makeText(NewCustomerActivity.this,getString(R.string.enter_valid_phone_no_),Toast.LENGTH_SHORT).show();
                  return;
                }
                else if(Pin.equals("")){
                    Toast.makeText(NewCustomerActivity.this,getString(R.string.enter_pin),Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(Company.equals("")){
                    Toast.makeText(NewCustomerActivity.this,getString(R.string.enter_company),Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(Country.equals("")){
                    Toast.makeText(NewCustomerActivity.this,getString(R.string.enter_country),Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(Tax.equals("")){
                    Toast.makeText(NewCustomerActivity.this,getString(R.string.enter_tax_details),Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    progress.setVisibility(View.VISIBLE);
                    LoginModel model = sh.getLoginModel(getString(R.string.login_model));

                    postNewCustomer(custmrname,add,em,phn,Pin,Company,Country,Tax);
                }
            }
        });
    }
    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final PlaceArrayAdapter.PlaceAutocomplete item = mPlaceArrayAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);
            Log.i(TAG, "Selected: " + item.description);
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
            Log.i(TAG, "Fetching details for ID: " + item.placeId);
        }
    };
    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                Log.e(TAG, "Place query did not complete. Error: " +
                        places.getStatus().toString());
                return;
            }
            // Selecting the first object buffer.
            final Place place = places.get(0);
            CharSequence attributions = places.getAttributions();
            Toast.makeText(NewCustomerActivity.this, place.getAddress(), Toast.LENGTH_SHORT).show();
        }
    };
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mPlaceArrayAdapter.setGoogleApiClient(mGoogleApiClient);
        Log.i(TAG, "Google Places API connected.");
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
    public void onConnectionSuspended(int i) {
        mPlaceArrayAdapter.setGoogleApiClient(null);
        Log.e(TAG, "Google Places API connection suspended.");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        Log.e(TAG, "Google Places API connection failed with error code: "
                + connectionResult.getErrorCode());

        Toast.makeText(this,
                "Google Places API connection failed with error code:" +
                        connectionResult.getErrorCode(),
                Toast.LENGTH_LONG).show();
    }

    public void postNewCustomer(String customerName,String address,String email,String phhone,String pin,String company,String country,String taxdetails )
    {
        LoginModel model = sh.getLoginModel(getString(R.string.login_model));
        Singleton.getInstance().getApi().addNewCustomer(model.getId(),customerName,address,email,phhone,pin,company,country,taxdetails,model.getAdmin_id()).enqueue(new Callback<ResMetaMeeting>() {
            @Override
            public void onResponse(Call<ResMetaMeeting> call, Response<ResMetaMeeting> response) {
                progress.setVisibility(View.GONE);
                Toast.makeText(NewCustomerActivity.this, getString(R.string.data_submited_successfully),
                        Toast.LENGTH_LONG).show();
                finish();
            }
            @Override
            public void onFailure(Call<ResMetaMeeting> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });
    }
    private void setTitle()
    {
        TextView title= (TextView) findViewById(R.id.title);
        title.setText(getString(R.string.add_customer));
    }

}
