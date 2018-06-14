package com.example.lenovo.trackapp.actv;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.trackapp.R;
import com.example.lenovo.trackapp.adaptor.CurrencyAdaptor;
import com.example.lenovo.trackapp.adaptor.DepartmentAdaptor;
import com.example.lenovo.trackapp.adaptor.MeetingsAdaptor;
import com.example.lenovo.trackapp.adaptor.PlaceArrayAdapter;
import com.example.lenovo.trackapp.adaptor.RequestTypesAdaptor;
import com.example.lenovo.trackapp.model.CurrencyModel;
import com.example.lenovo.trackapp.model.DepartmentModel;
import com.example.lenovo.trackapp.model.LoginModel;
import com.example.lenovo.trackapp.model.MeetingModel;
import com.example.lenovo.trackapp.model.RequestTypeModel;
import com.example.lenovo.trackapp.model.ResMetaCurrency;
import com.example.lenovo.trackapp.model.ResMetaDepartment;
import com.example.lenovo.trackapp.model.ResMetaMeeting;
import com.example.lenovo.trackapp.model.ResMetaReqTypes;
import com.example.lenovo.trackapp.util.Shprefrences;
import com.example.lenovo.trackapp.util.Singleton;
import com.example.lenovo.trackapp.util.Util;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Lenovo on 22-05-2018.
 */

public class AddPreRequestActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks, SearchView.OnQueryTextListener {
    EditText edtMeetings, edtDescreption, edtAdvance, edtDepartment, edtCurrency;
    Button btnSubmit;
    ProgressBar progress;
    Shprefrences sh;
    ArrayList<MeetingModel> meetingList = new ArrayList<>();
    ArrayList<RequestTypeModel> requestTyoesList = new ArrayList<>();
    ArrayList<DepartmentModel> departmentList = new ArrayList<>();
    ArrayList<CurrencyModel> currencyList = new ArrayList<>();
    ListView listTypes;
    private AutoCompleteTextView edtAddress;
    private static final String TAG = "CreateMeetingActivity";
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private GoogleApiClient mGoogleApiClient;
    private PlaceArrayAdapter mPlaceArrayAdapter;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_prerequest_activity);
        edtMeetings = findViewById(R.id.edtMeetings);
        edtDescreption = findViewById(R.id.edtDescreption);
        edtAdvance = findViewById(R.id.edtAdvance);
        btnSubmit = findViewById(R.id.btnSubmit);
        listTypes = findViewById(R.id.listTypes);
        edtDepartment = findViewById(R.id.edtDepartment);
        edtCurrency = findViewById(R.id.edtCurrency);
        getSupportActionBar().setTitle("Add Pre-Request");
        edtAddress = (AutoCompleteTextView) findViewById(R.id.edt_location);
        progress = findViewById(R.id.progress);
        sh = new Shprefrences(this);
        progress.setVisibility(View.VISIBLE);
        getReqestTypes();
        getMeetingsList();
        getDepartmentList();
        getCurrencyList();
       /* listTypes.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
*/
        mGoogleApiClient = new GoogleApiClient.Builder(AddPreRequestActivity.this)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, GOOGLE_API_CLIENT_ID, this)
                .addConnectionCallbacks(this)
                .build();
        edtAddress.setOnItemClickListener(mAutocompleteClickListener);
        mPlaceArrayAdapter = new PlaceArrayAdapter(
                this, android.R.layout.simple_list_item_1,
                BOUNDS_MOUNTAIN_VIEW, null);
        edtAddress.setAdapter(mPlaceArrayAdapter);
        edtAddress.setThreshold(1);
        //edtAdvance.setFilters(new InputFilter[] {new CurrencyFormatInputFilter()});
        edtMeetings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMeetings();
            }
        });
        edtDepartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDepartmentList();
            }
        });
        edtCurrency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCurrencyList();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitPost();
            }
        });

      /*  listTypes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                RequestTypeModel obj = (RequestTypeModel) listTypes.getAdapter().getItem(position);
                ImageView chkSelect=view.findViewById(R.id.imgSelect);
                if(obj.isSelected())
                {
                    obj.setSelected(false);
                    chkSelect.setVisibility(View.GONE);
                }
                else
                {
                    obj.setSelected(true);
                    chkSelect.setVisibility(View.VISIBLE);
                }
            }
        });*/
    }

    public void getMeetingsList() {
        LoginModel model = sh.getLoginModel("LOGIN_MODEL");
        Singleton.getInstance().getApi().getMeetingsList(model.getId()).enqueue(new Callback<ResMetaMeeting>() {
            @Override
            public void onResponse(Call<ResMetaMeeting> call, Response<ResMetaMeeting> response) {
                meetingList = response.body().getResponse();
            }

            @Override
            public void onFailure(Call<ResMetaMeeting> call, Throwable throwable) {
            }
        });
    }

    public void getDepartmentList() {
        LoginModel model = sh.getLoginModel("LOGIN_MODEL");
        Singleton.getInstance().getApi().getDepartmentList(model.getId()).enqueue(new Callback<ResMetaDepartment>() {
            @Override
            public void onResponse(Call<ResMetaDepartment> call, Response<ResMetaDepartment> response) {
                departmentList = response.body().getResponse();
            }

            @Override
            public void onFailure(Call<ResMetaDepartment> call, Throwable t) {

            }
        });
    }

    private void getCurrencyList() {
        LoginModel model = sh.getLoginModel("LOGIN_MODEL");
        Singleton.getInstance().getApi().getCurrencyList(model.getUser_id()).enqueue(new Callback<ResMetaCurrency>() {
            @Override
            public void onResponse(Call<ResMetaCurrency> call, Response<ResMetaCurrency> response) {
                currencyList = response.body().getResponse();
            }
            @Override
            public void onFailure(Call<ResMetaCurrency> call, Throwable t) {
            }
        });
    }
    public void getReqestTypes() {
        LoginModel model = sh.getLoginModel("LOGIN_MODEL");
        Singleton.getInstance().getApi().getRequestTypes(model.getId()).enqueue(new Callback<ResMetaReqTypes>() {
            @Override
            public void onResponse(Call<ResMetaReqTypes> call, Response<ResMetaReqTypes> response) {
                requestTyoesList = response.body().getResponse();
                RequestTypesAdaptor adapto = new RequestTypesAdaptor(com.example.lenovo.trackapp.actv.AddPreRequestActivity.this, requestTyoesList);
                listTypes.setAdapter(adapto);
                Util.setListViewHeightBasedOnItems(listTypes);
                progress.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(Call<ResMetaReqTypes> call, Throwable throwable) {
                progress.setVisibility(View.GONE);
            }
        });
    }
    AlertDialog alertDialog;
    MeetingsAdaptor adaptor;
    private int popupId = 0;
    private void showMeetings() {
        adaptor = new MeetingsAdaptor(com.example.lenovo.trackapp.actv.AddPreRequestActivity.this, meetingList);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        // ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.meeting_popup, null);
        final ListView listPurpose = dialogView.findViewById(R.id.listPurpose);
        TextView title = dialogView.findViewById(R.id.title);
        final SearchView editTextName = dialogView.findViewById(R.id.edt);
        editTextName.setQueryHint("Search Here");
        editTextName.setOnQueryTextListener(this);
        //Button btnUpgrade = (Button) dialogView.findViewById(R.id.btnUpgrade);
        title.setText("Selected Created Meetings");
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        popupId = 1;
        alertDialog.show();
        listPurpose.setAdapter(adaptor);
        listPurpose.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                MeetingModel obj = (MeetingModel) listPurpose.getAdapter().getItem(position);
                Log.e("selected**", "" + obj.getDescreption());
                edtMeetings.setText(obj.getDescreption());
                meetingId = obj.getId();
                alertDialog.dismiss();
            }
        });
    }
    String meetingId = "";
    DepartmentAdaptor departmentAdaptor;
    private void showDepartmentList() {
        departmentAdaptor = new DepartmentAdaptor(AddPreRequestActivity.this, departmentList);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        // ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.meeting_popup, null);
        final ListView listPurpose = dialogView.findViewById(R.id.listPurpose);
        TextView title = dialogView.findViewById(R.id.title);
        final SearchView editTextName = dialogView.findViewById(R.id.edt);
        editTextName.setIconified(true);
        editTextName.setQueryHint("Search Here");
        editTextName.setOnQueryTextListener(this);
        title.setText("Select Department");
        //Button btnUpgrade = (Button) dialogView.findViewById(R.id.btnUpgrade);
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        popupId = 3;
        alertDialog.show();
        listPurpose.setAdapter(departmentAdaptor);

        listPurpose.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                DepartmentModel obj = (DepartmentModel) listPurpose.getAdapter().getItem(position);
                edtDepartment.setText(obj.getDepartment_name());
                alertDialog.dismiss();
            }
        });
    }
    CurrencyAdaptor currencyAdaptor;
    private void showCurrencyList() {
        currencyAdaptor = new CurrencyAdaptor(AddPreRequestActivity.this, currencyList);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        // ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.meeting_popup, null);
        final ListView listPurpose = dialogView.findViewById(R.id.listPurpose);
        final SearchView editTextName = dialogView.findViewById(R.id.edt);
        TextView title = dialogView.findViewById(R.id.title);
        editTextName.setQueryHint("Search Here");
        editTextName.setOnQueryTextListener(this);
        title.setText("Select Currency");
        //Button btnUpgrade = (Button) dialogView.findViewById(R.id.btnUpgrade);
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        popupId = 2;
        alertDialog.show();
        listPurpose.setAdapter(currencyAdaptor);
        listPurpose.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                CurrencyModel obj = (CurrencyModel) listPurpose.getAdapter().getItem(position);
                edtCurrency.setText(obj.getCurrency_name());
                alertDialog.dismiss();
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
                ArrayList<MeetingModel> newlist = new ArrayList<>();
                for (MeetingModel list : meetingList) {
                    String getName = list.getDescreption().toLowerCase();

                    if (getName.contains(s)) {
                        newlist.add(list);
                    }
                }
                adaptor.filter(newlist);
                break;
            case 2:
                ArrayList<CurrencyModel> newlist1 = new ArrayList<>();
                for (CurrencyModel list : currencyList) {
                    String getCurrency = list.getCurrency_name().toLowerCase();

                    if (getCurrency.contains(s)) {
                        newlist1.add(list);
                    }
                }
                currencyAdaptor.filter(newlist1);
                break;
            case 3:
                ArrayList<DepartmentModel> newlist2 = new ArrayList<>();
                for (DepartmentModel list : departmentList) {
                    String getDepartment = list.getDepartment_name().toLowerCase();

                    if (getDepartment.contains(s)) {
                        newlist2.add(list);
                    }
                }
                departmentAdaptor.filter(newlist2);
                break;
        }
        return true;
    }
    private void submitPost() {
        LoginModel model = sh.getLoginModel("LOGIN_MODEL");
        String userid = model.getId();
        String adv = edtAdvance.getText().toString();
        String curr = edtCurrency.getText().toString();
        String dept = edtDepartment.getText().toString();
        String des = edtDescreption.getText().toString();
        String totalamount = adv + curr;
        if (userid.equals("")) {
            Toast.makeText(AddPreRequestActivity.this, "Your session is time out please login again", Toast.LENGTH_SHORT).show();
            return;
        } else if (adv.equals("")) {
            Toast.makeText(AddPreRequestActivity.this, "Enter Advance Amount", Toast.LENGTH_SHORT).show();
            return;
        } else if (curr.equals("")) {
            Toast.makeText(AddPreRequestActivity.this, "Select Currency", Toast.LENGTH_SHORT).show();
            return;
        } else if (dept.equals("")) {
            Toast.makeText(AddPreRequestActivity.this, "Select Department", Toast.LENGTH_SHORT).show();
            return;
        } else if (meetingId.equals("")) {
            Toast.makeText(AddPreRequestActivity.this, "Select Meeting", Toast.LENGTH_SHORT).show();
            return;
        } else if (des.equals("")) {
            Toast.makeText(AddPreRequestActivity.this, "Enter Descreption", Toast.LENGTH_SHORT).show();
            return;
        }
        String addres = edtAddress.getText() + "";
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String datetime = dateFormat.format(date);
        Log.e("datetime", "**********" + datetime);
        Log.e("addres", "*****************" + addres);
        ArrayList<RequestTypeModel>listtype=new ArrayList<>();
        for(RequestTypeModel list :requestTyoesList)
        {
            if(list.isSelected())
                listtype.add(list);
        }
        progress.setVisibility(View.VISIBLE);
        Singleton.getInstance().getApi().postPreRequest(userid, totalamount, curr, dept, meetingId, des, addres, datetime, listtype).enqueue(new Callback<ResMetaMeeting>() {
            @Override
            public void onResponse(Call<ResMetaMeeting> call, Response<ResMetaMeeting> response) {
                progress.setVisibility(View.GONE);
                Toast.makeText(AddPreRequestActivity.this, "Pre-Request Submited Successfully!", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ResMetaMeeting> call, Throwable t) {
                progress.setVisibility(View.GONE);
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
            Toast.makeText(AddPreRequestActivity.this, place.getAddress(),Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mPlaceArrayAdapter.setGoogleApiClient(mGoogleApiClient);
        Log.i(TAG, "Google Places API connected.");
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

}