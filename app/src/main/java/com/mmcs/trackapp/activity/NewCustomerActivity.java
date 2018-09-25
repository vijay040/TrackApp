package com.mmcs.trackapp.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import com.mmcs.trackapp.R;
import com.mmcs.trackapp.adaptor.CompanyAdaptar;
import com.mmcs.trackapp.adaptor.CountryAdaptar;
import com.mmcs.trackapp.adaptor.LocationAdapter;
import com.mmcs.trackapp.adaptor.PayTermAdapter;
import com.mmcs.trackapp.adaptor.PlaceArrayAdapter;
import com.mmcs.trackapp.adaptor.PurposePopupAdaptor;
import com.mmcs.trackapp.model.CompanyModel;
import com.mmcs.trackapp.model.CountryModel;
import com.mmcs.trackapp.model.LocationModel;
import com.mmcs.trackapp.model.LoginModel;
import com.mmcs.trackapp.model.PayTermModel;
import com.mmcs.trackapp.model.PurposeModel;
import com.mmcs.trackapp.model.ResMetaMeeting;
import com.mmcs.trackapp.model.RestMetaCompany;
import com.mmcs.trackapp.model.RestMetaCountry;
import com.mmcs.trackapp.model.RestMetaLocation;
import com.mmcs.trackapp.model.RestMetaPayTerm;
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

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class NewCustomerActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks,SearchView.OnQueryTextListener {
    EditText customername,email,edt_place,edt_city,edt_customer_company,phone,pin,edt_payterm,companyname,country,edt_location,taxdetails;
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
        edt_place=findViewById(R.id.edt_place);
        edt_city=findViewById(R.id.edt_city);
        edt_payterm=findViewById(R.id.edt_payterm);
        companyname=findViewById(R.id.edt_company);
        progress = findViewById(R.id.progress);
        country=findViewById(R.id.edt_country);
        edt_location=findViewById(R.id.edt_location);
        taxdetails=findViewById(R.id.edt_taxdetails);
        edt_customer_company=findViewById(R.id.edt_customer_company);
        submit=findViewById(R.id.btnSubmit);
        back();
        setTitle();
        getPayTermList();
        getCompanyList();
        getCountryList();
        getLocationList();
        progress.setVisibility(View.VISIBLE);
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
                String payterm=edt_payterm.getText().toString();
                String location=edt_location.getText().toString();
                String place=edt_place.getText().toString();
                String city=edt_city.getText().toString();
                String customer_company=edt_customer_company.getText().toString();
                if(custmrname.equals("")){
                    Toast.makeText(NewCustomerActivity.this,getString(R.string.enter_customer_name),Toast.LENGTH_SHORT).show();
                }
                else if(add.equals("")){
                    Toast.makeText(NewCustomerActivity.this,getString(R.string.enter_address),Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(place.equals("")){
                    Toast.makeText(NewCustomerActivity.this,getString(R.string.enter_place),Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(city.equals("")){
                    Toast.makeText(NewCustomerActivity.this,getString(R.string.enter_city),Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(em.equals("")) {

                    Toast.makeText(NewCustomerActivity.this,getString(R.string.enter_valid_email),Toast.LENGTH_SHORT).show();
                 return;
                }
                else if(Pin.equals("")){
                    Toast.makeText(NewCustomerActivity.this,getString(R.string.enter_pin),Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(customer_company.equals("")){
                    Toast.makeText(NewCustomerActivity.this,getString(R.string.enter_customer_company),Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(payterm.equals("")){
                    Toast.makeText(NewCustomerActivity.this,getString(R.string.select_payterm),Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(phn.trim().isEmpty()||phone.getText().toString().length()<10||phn.length()>12){
                    Toast.makeText(NewCustomerActivity.this,getString(R.string.enter_valid_phone_no_),Toast.LENGTH_SHORT).show();
                  return;
                }
                else if(Tax.equals("")){
                    Toast.makeText(NewCustomerActivity.this,getString(R.string.enter_tax_details),Toast.LENGTH_SHORT).show();
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
                else if(location.equals("")){
                    Toast.makeText(NewCustomerActivity.this,getString(R.string.enter_location),Toast.LENGTH_SHORT).show();
                    return;
                }

                else {
                    progress.setVisibility(View.VISIBLE);
                    LoginModel model = sh.getLoginModel(getString(R.string.login_model));

                    postNewCustomer(custmrname,add,em,phn,Pin,customer_company,Tax,place,city);
                }
            }
        });
        edt_payterm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPayTermPopup();
            }
        });
        companyname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCompanyPopup();
            }
        });
        country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCountryPopup();
            }
        });
        edt_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLocationPopup();
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

    public void postNewCustomer(String customerName,String address,String email,String phhone,String pin,String customer_company,String taxdetails,String place,String city )
    {
        LoginModel model = sh.getLoginModel(getString(R.string.login_model));
        Singleton.getInstance().getApi().addNewCustomer(model.getId(),customerName,address,place,city,email,phhone,pin,customer_company,companyId,countryId,loactionId,paytermId,taxdetails,model.getAdmin_id()).enqueue(new Callback<ResMetaMeeting>() {
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
    AlertDialog alertDialog;
    ArrayList<PayTermModel> paytermList = new ArrayList<>();
    PayTermAdapter payTermAdapter;
    private int popupId = 0;
    String paytermId;
    private void showPayTermPopup() {

        payTermAdapter = new PayTermAdapter(NewCustomerActivity.this, paytermList);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        // ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.meeting_popup, null);
        final ListView listPayterm = dialogView.findViewById(R.id.listPurpose);
        TextView title = dialogView.findViewById(R.id.title);
        final SearchView editTextName = dialogView.findViewById(R.id.edt);
        editTextName.setQueryHint(getString(R.string.search_here));
        editTextName.setOnQueryTextListener(this);
        title.setText(getString(R.string.select_payterm));
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        alertDialog.show();
        popupId = 1;
        listPayterm.setAdapter(payTermAdapter);

        listPayterm.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                PayTermModel obj = (PayTermModel) listPayterm.getAdapter().getItem(position);
                edt_payterm.setText(obj.getPay_term_descrp());
                paytermId=obj.getId();
                alertDialog.dismiss();
            }
        });

    }
    ArrayList<CompanyModel> listCompany;
    CompanyAdaptar companyAdaptar;

String companyId;
    private void showCompanyPopup() {

        companyAdaptar = new CompanyAdaptar(NewCustomerActivity.this, listCompany);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        // ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.meeting_popup, null);
        final ListView listCustomer = dialogView.findViewById(R.id.listPurpose);
        TextView title = dialogView.findViewById(R.id.title);
        final SearchView editTextName = dialogView.findViewById(R.id.edt);
        editTextName.setQueryHint(getString(R.string.search_here));
        editTextName.setOnQueryTextListener(this);
        title.setText(getString(R.string.select_company));
        //Button btnUpgrade = (Button) dialogView.findViewById(R.id.btnUpgrade);
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        popupId = 2;
        alertDialog.show();
        listCustomer.setAdapter(companyAdaptar);

        listCustomer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                CompanyModel obj = (CompanyModel) listCustomer.getAdapter().getItem(position);
                companyname.setText(obj.getCompany_name());
                companyId = obj.getId();

                alertDialog.dismiss();
            }
        });

    }

    ArrayList<CountryModel> listCountry;
    CountryAdaptar countryAdaptar;
    String countryId;
    private void showCountryPopup() {

        countryAdaptar = new CountryAdaptar(NewCustomerActivity.this, listCountry);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        // ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.meeting_popup, null);
        final ListView listCountry = dialogView.findViewById(R.id.listPurpose);
        TextView title = dialogView.findViewById(R.id.title);
        final SearchView editTextName = dialogView.findViewById(R.id.edt);
        editTextName.setQueryHint(getString(R.string.search_here));
        editTextName.setOnQueryTextListener(this);
        title.setText(getString(R.string.select_country));
        //Button btnUpgrade = (Button) dialogView.findViewById(R.id.btnUpgrade);
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        popupId = 3;
        alertDialog.show();
        listCountry.setAdapter(countryAdaptar);

        listCountry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                CountryModel obj = (CountryModel) listCountry.getAdapter().getItem(position);
                country.setText(obj.getCountry());
                countryId=obj.getId();
                alertDialog.dismiss();
            }
        });

    }
    ArrayList<LocationModel> listLocation;
    LocationAdapter locationAdapter;
    String loactionId;
    private void showLocationPopup() {

        locationAdapter = new LocationAdapter(NewCustomerActivity.this, listLocation);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        // ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.meeting_popup, null);
        final ListView listLocation = dialogView.findViewById(R.id.listPurpose);
        TextView title = dialogView.findViewById(R.id.title);
        final SearchView editTextName = dialogView.findViewById(R.id.edt);
        editTextName.setQueryHint(getString(R.string.search_here));
        editTextName.setOnQueryTextListener(this);
        title.setText(getString(R.string.select_location));
        //Button btnUpgrade = (Button) dialogView.findViewById(R.id.btnUpgrade);
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        popupId = 4;
        alertDialog.show();
        listLocation.setAdapter(locationAdapter);

        listLocation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                LocationModel obj = (LocationModel) listLocation.getAdapter().getItem(position);
                edt_location.setText(obj.getLocation());
                loactionId=obj.getId();
                alertDialog.dismiss();
            }
        });

    }

    public void getPayTermList() {
        Singleton.getInstance().getApi().getPayTermList("").enqueue(new Callback<RestMetaPayTerm>() {
            @Override
            public void onResponse(Call<RestMetaPayTerm> call, Response<RestMetaPayTerm> response) {

                paytermList = response.body().getResponse();
                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<RestMetaPayTerm> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });
    }
    public void getCompanyList() {
        Singleton.getInstance().getApi().getCompanyList("").enqueue(new Callback<RestMetaCompany>() {
            @Override
            public void onResponse(Call<RestMetaCompany> call, Response<RestMetaCompany> response) {

                listCompany = response.body().getResponse();
                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<RestMetaCompany> call, Throwable t) {
                progress.setVisibility(View.GONE);

            }
        });
    }
    public void getCountryList() {
        Singleton.getInstance().getApi().getCountryList("").enqueue(new Callback<RestMetaCountry>() {
            @Override
            public void onResponse(Call<RestMetaCountry> call, Response<RestMetaCountry> response) {

                listCountry = response.body().getResponse();
                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<RestMetaCountry> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });
    }
    public void getLocationList() {
        Singleton.getInstance().getApi().getLocationList("").enqueue(new Callback<RestMetaLocation>() {
            @Override
            public void onResponse(Call<RestMetaLocation> call, Response<RestMetaLocation> response) {

                listLocation = response.body().getResponse();
                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<RestMetaLocation> call, Throwable t) {
                progress.setVisibility(View.GONE);

            }
        });
    }



    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        s = s.toLowerCase();
        switch (popupId) {
            case 1:
                ArrayList<PayTermModel> newlist = new ArrayList<>();
                for (PayTermModel list : paytermList) {
                    String getPayTerm = list.getPay_term_descrp().toLowerCase();

                    if (getPayTerm.contains(s)) {
                        newlist.add(list);
                    }
                }
                payTermAdapter.filter(newlist);
                break;

            case 2:
                ArrayList<CompanyModel> newlist1 = new ArrayList<>();
                for (CompanyModel list : listCompany) {
                    String getCompany = list.getCompany_name().toLowerCase();
                    if (getCompany.contains(s)) {
                        newlist1.add(list);
                    }
                }
                companyAdaptar.filter(newlist1);
                break;
            case 3:
                ArrayList<CountryModel> newlist2 = new ArrayList<>();
                for (CountryModel list : listCountry) {
                    String getCompany = list.getCountry().toLowerCase();
                    if (getCompany.contains(s)) {
                        newlist2.add(list);
                    }
                }
                countryAdaptar.filter(newlist2);
                break;
            case 4:
                ArrayList<LocationModel> newlist3 = new ArrayList<>();
                for (LocationModel list : listLocation) {
                    String getLocation = list.getLocation().toLowerCase();
                    if (getLocation.contains(s)) {
                        newlist3.add(list);
                    }
                }
                locationAdapter.filter(newlist3);
                break;
        }
        return false;
    }
}
