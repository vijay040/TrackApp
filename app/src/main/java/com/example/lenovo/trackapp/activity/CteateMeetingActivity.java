package com.example.lenovo.trackapp.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.lenovo.trackapp.adaptor.CustomerPopupAdaptor;
import com.example.lenovo.trackapp.adaptor.MeetingsPopupAdaptor;
import com.example.lenovo.trackapp.adaptor.MostRecentlyUsedPopupAdaptor;
import com.example.lenovo.trackapp.adaptor.PlaceArrayAdapter;
import com.example.lenovo.trackapp.adaptor.PurposePopupAdaptor;
import com.example.lenovo.trackapp.adaptor.TransportationPopupAdaptor;
import com.example.lenovo.trackapp.db.CreateMeetDB;
import com.example.lenovo.trackapp.R;
import com.example.lenovo.trackapp.model.CustomerModel;
import com.example.lenovo.trackapp.model.MeetingsModel;
import com.example.lenovo.trackapp.model.PurposeModel;
import com.example.lenovo.trackapp.model.ResMetaCustomer;
import com.example.lenovo.trackapp.model.ResponseMeta;
import com.example.lenovo.trackapp.model.TransportationModel;
import com.example.lenovo.trackapp.util.Singleton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import org.w3c.dom.Text;
import java.util.ArrayList;
import java.util.Calendar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CteateMeetingActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks {
    private static final String TAG = "CteateMeetingActivity";
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private AutoCompleteTextView edtAddress;
    private GoogleApiClient mGoogleApiClient;
    private PlaceArrayAdapter mPlaceArrayAdapter;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));
    int H, M;
    Calendar calendar;
    int DD, MM, YY;
    EditText edtPurpose, edtDescreption, edtCustomer, edtDate, edtTime, edtAgenda, edtContactperson;
    CreateMeetDB database = new CreateMeetDB(this);
    TextView txtReminder;
    Button btnSubmit;
    ProgressBar progress;
    public static  String SELECTED_PURPOSE;

    //SweetAlertDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cteate_meeting);
        edtPurpose = findViewById(R.id.edtPurpose);
        edtDescreption = findViewById(R.id.edtDescreption);
        edtCustomer = findViewById(R.id.edtCustomer);
        edtDate = findViewById(R.id.edtDate);
        edtTime = findViewById(R.id.edtTime);
        edtAgenda = findViewById(R.id.edtAgenda);
        edtContactperson = findViewById(R.id.edtContactperson);
        edtAddress = findViewById(R.id.edtAddress);
        txtReminder = findViewById(R.id.txtReminder);
        btnSubmit = findViewById(R.id.btnSubmit);
        progress = findViewById(R.id.progress);
        mGoogleApiClient = new GoogleApiClient.Builder(CteateMeetingActivity.this)
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
        calendar = Calendar.getInstance();
        DD = calendar.get(Calendar.DAY_OF_MONTH);
        MM = calendar.get(Calendar.MONTH);
        YY = calendar.get(Calendar.YEAR);
        edtDate.setText(String.valueOf(DD) + "-" + String.valueOf(MM + 1) + "-" + String.valueOf(YY));
        H = calendar.get(Calendar.HOUR_OF_DAY);
        M = calendar.get(Calendar.MINUTE);
        if (H < 12 && H >= 0) {
            edtTime.setText(String.valueOf(H) + ":" + String.valueOf(M) + " " + "AM");
        } else {
            H -= 12;
            if (H == 0) {
                H = 12;
            }
            edtTime.setText(String.valueOf(H) + ":" + String.valueOf(M) + " " + "PM");
        }
        edtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                showDialog(121);
            }
        });
        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                showDialog(111);
            }
        });

        edtCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomerPopup();
            }
        });

        getPurposeList();
        getCustomerList();

      /*  pDialog= new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
*/
        edtPurpose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPurposePopup();
            }
        });
        txtReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CteateMeetingActivity.this, ReminderActivity.class));
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String descreption = edtDescreption.getText().toString();
                String purpose = edtPurpose.getText().toString();
                String customer = edtCustomer.getText().toString();
                String date = edtDate.getText().toString();
                String time = edtTime.getText().toString();
                String agenda = edtAgenda.getText().toString();
                String contactperson = edtContactperson.getText().toString();
                String address = edtAddress.getText().toString();
                if (purpose.equals("")) {
                    Toast.makeText(CteateMeetingActivity.this, "Select Purpose", Toast.LENGTH_SHORT).show();
                } else if (descreption.equals("")) {
                    Toast.makeText(CteateMeetingActivity.this, "Enter Descreption", Toast.LENGTH_SHORT).show();
                } else if (customer.equals("")) {
                    Toast.makeText(CteateMeetingActivity.this, "Enter Customer Name", Toast.LENGTH_SHORT).show();
                } else if (date.equals("")) {
                    Toast.makeText(CteateMeetingActivity.this, "Enter Date", Toast.LENGTH_SHORT).show();
                } else if (time.equals("")) {
                    Toast.makeText(CteateMeetingActivity.this, "Enter Time", Toast.LENGTH_SHORT).show();
                } else if (agenda.equals("")) {
                    Toast.makeText(CteateMeetingActivity.this, "Enter Agenda", Toast.LENGTH_SHORT).show();
                } else if (contactperson.equals("")) {
                    Toast.makeText(CteateMeetingActivity.this, "Enter Contact Person", Toast.LENGTH_SHORT).show();
                } else if (address.equals("")) {
                    Toast.makeText(CteateMeetingActivity.this, "Enter Address", Toast.LENGTH_SHORT).show();
                } else

                {
                    // pDialog.show();
                    progress.setVisibility(View.VISIBLE);
                    createMeeting("", purpose, descreption, customer, date, time, agenda, contactperson, address, "", "", "", "", "");
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.addcustomer, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        int itemid = item.getItemId();
        if (itemid == R.id.add_customer) {
            Intent intent = new Intent(CteateMeetingActivity.this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == 111) {
            return new DatePickerDialog(this, onDateSetListener, YY, MM, DD);
        } else if (id == 121) {
            return new TimePickerDialog(this, onTimeSetListener, H, M, false);
        }
        return onCreateDialog(id);
    }

    DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int d, int m, int y) {
            edtDate.setText(String.valueOf(d) + "-" + String.valueOf(m + 1) + "-" + String.valueOf(y));
        }
    };
    TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int h, int m) {
            if (h < 12 && h >= 0) {
                edtTime.setText(String.valueOf(h) + ":" + String.valueOf(m) + " " + "AM");
            } else {
                h -= 12;
                if (h == 0) {
                    h = 12;
                }
                edtTime.setText(String.valueOf(h) + ":" + String.valueOf(m) + " " + "PM");
            }
        }
    };

    public void getPurposeList()
    {
        Singleton.getInstance().getApi().getPurposeList("").enqueue(new Callback<ResponseMeta>() {
            @Override
            public void onResponse(Call<ResponseMeta> call, Response<ResponseMeta> response) {

                purposeList=response.body().getResponse();
            }

            @Override
            public void onFailure(Call<ResponseMeta> call, Throwable t) {

            }
        });
    }


    public void getCustomerList()
    {
        Singleton.getInstance().getApi().getCustomerList("").enqueue(new Callback<ResMetaCustomer>() {
            @Override
            public void onResponse(Call<ResMetaCustomer> call, Response<ResMetaCustomer> response) {

                listCustomer=response.body().getResponse();
            }

            @Override
            public void onFailure(Call<ResMetaCustomer> call, Throwable t) {

            }
        });
    }


    AlertDialog alertDialog;
    ArrayList<PurposeModel> purposeList = new ArrayList<>();
    PurposePopupAdaptor adaptor;

    private void showPurposePopup() {
       /* PurposeModel m=new PurposeModel();
        m.setPurpose("Business Meeting in Noida");
        m.setId("0");

        purposeList.add(m);*/
        adaptor = new PurposePopupAdaptor(CteateMeetingActivity.this, purposeList);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        // ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.meeting_popup, null);
        final ListView listPurpose = dialogView.findViewById(R.id.listPurpose);
        //Button btnUpgrade = (Button) dialogView.findViewById(R.id.btnUpgrade);
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        alertDialog.show();
        listPurpose.setAdapter(adaptor);

        listPurpose.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                PurposeModel obj = (PurposeModel) listPurpose.getAdapter().getItem(position);
                edtPurpose.setText(obj.getPurpose());
                alertDialog.dismiss();
            }
        });

    }

    ArrayList<CustomerModel>listCustomer;

    private void showCustomerPopup() {
       /* PurposeModel m=new PurposeModel();
        m.setPurpose("Business Meeting in Noida");
        m.setId("0");

        purposeList.add(m);*/
        CustomerPopupAdaptor adapto = new CustomerPopupAdaptor(CteateMeetingActivity.this, listCustomer);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        // ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.meeting_popup, null);
        final ListView listPurpose = dialogView.findViewById(R.id.listPurpose);
       TextView title= dialogView.findViewById(R.id.title);
        title.setText("Select Customer");
        //Button btnUpgrade = (Button) dialogView.findViewById(R.id.btnUpgrade);
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        alertDialog.show();
        listPurpose.setAdapter(adapto);

        listPurpose.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                CustomerModel obj = (CustomerModel) listPurpose.getAdapter().getItem(position);
                edtCustomer.setText(obj.getCustomer_name());
                alertDialog.dismiss();
            }
        });

    }



    private void createMeeting(String user_id, String purpose, String descreption
            , String customer
            , String date
            , String time
            , String agenda
            , String contact_person
            , String address
            , String start_date
            , String start_time
            , String end_date
            , String end_time
            , String alarm_time

    ) {
        Singleton.getInstance().getApi().postMeeting(user_id, purpose
                , descreption
                , customer
                , date
                , time
                , agenda
                , contact_person
                , address
                , start_date
                , start_time
                , end_date
                , end_time
                , alarm_time

        ).enqueue(new Callback<MeetingsModel>() {
            @Override
            public void onResponse(Call<MeetingsModel> call, Response<MeetingsModel> response) {
                progress.setVisibility(View.GONE);
                Toast.makeText(CteateMeetingActivity.this, "Data submited successfully!",
                        Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onFailure(Call<MeetingsModel> call, Throwable t) {
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
            Toast.makeText(CteateMeetingActivity.this,place.getAddress(),Toast.LENGTH_SHORT).show();

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
