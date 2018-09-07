package com.mmcs.trackapp.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.mmcs.trackapp.adaptor.CustomerPopupAdaptor;
import com.mmcs.trackapp.adaptor.PlaceArrayAdapter;
import com.mmcs.trackapp.adaptor.PurposePopupAdaptor;
import com.mmcs.trackapp.R;
import com.mmcs.trackapp.model.AlarmModel;
import com.mmcs.trackapp.model.CustomerModel;
import com.mmcs.trackapp.model.LoginModel;
import com.mmcs.trackapp.model.MeetingModel;
import com.mmcs.trackapp.model.PurposeModel;
import com.mmcs.trackapp.model.ResMetaCustomer;
import com.mmcs.trackapp.model.ResponseMeta;
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
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateMeetingActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks, SearchView.OnQueryTextListener {
    private static final String TAG = "CreateMeetingActivity";
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private AutoCompleteTextView edtAddress;
    private GoogleApiClient mGoogleApiClient;
    private PlaceArrayAdapter mPlaceArrayAdapter;
    CheckBox chk_address;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));
    int H, M;
    Calendar calendar;
    int DD, MM, YY;
    EditText edtPurpose, edtDescreption, edtCustomer, edtDate, edtTime, edtAgenda, edtContactperson;

    TextView txtReminder;
    Button btnSubmit;
    ProgressBar progress;
    public static String SELECTED_PURPOSE;
    Shprefrences sh;

    //SweetAlertDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_meeting);
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
        sh = new Shprefrences(this);
        chk_address=findViewById(R.id.chk_address);
        chk_address.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    edtAddress.setText("NOIDA SECTOR 63");
                    edtAddress.setEnabled(false);
                } else {
                    edtAddress.getText().clear();
                  edtAddress.setEnabled(true);
                }
            }
        });
        back();
        setTitle();
        //getSupportActionBar().setTitle("Create Meeting");
        mGoogleApiClient = new GoogleApiClient.Builder(CreateMeetingActivity.this)
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

        if ((MM + 1) < 10)
            edtDate.setText(String.valueOf(YY) + "-0" + String.valueOf(MM + 1) + "-" + String.valueOf(DD));
        else
            edtDate.setText(String.valueOf(YY) + "-" + String.valueOf(MM + 1) + "-" + String.valueOf(DD));
        H = calendar.get(Calendar.HOUR_OF_DAY);
        M = calendar.get(Calendar.MINUTE);
        edtTime.setText(String.valueOf(H) + ":" + String.valueOf(M));
     /* if (H < 12 && H >= 0) {
            edtTime.setText(String.valueOf(H) + ":" + String.valueOf(M) + " " + "AM");
        } else {
            H -= 12;
            if (H == 0) {
                H = 12;
            }
            edtTime.setText(String.valueOf(H) + ":" + String.valueOf(M) + " " + "PM");
        }*/
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
        getCustomerList();
        getPurposeList();

        edtPurpose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPurposePopup();
            }
        });
        txtReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReminderActivity.startDate = edtDate.getText() + "";
                ReminderActivity.startTime = edtTime.getText() + "";
                startActivityForResult(new Intent(CreateMeetingActivity.this, ReminderActivity.class), 10);
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
                    Toast.makeText(CreateMeetingActivity.this, getString(R.string.select_purpose), Toast.LENGTH_SHORT).show();
                } else if (descreption.equals("")) {
                    Toast.makeText(CreateMeetingActivity.this, getString(R.string.enter_descreption), Toast.LENGTH_SHORT).show();
                } else if (customer.equals("")) {
                    Toast.makeText(CreateMeetingActivity.this, getString(R.string.enter_customer_name), Toast.LENGTH_SHORT).show();
                } else if (date.equals("")) {
                    Toast.makeText(CreateMeetingActivity.this, getString(R.string.enter_date), Toast.LENGTH_SHORT).show();
                } else if (time.equals("")) {
                    Toast.makeText(CreateMeetingActivity.this, getString(R.string.enter_time), Toast.LENGTH_SHORT).show();
                } else if (agenda.equals("")) {
                    Toast.makeText(CreateMeetingActivity.this, getString(R.string.enter_agenda), Toast.LENGTH_SHORT).show();
                } else if (contactperson.equals("")) {
                    Toast.makeText(CreateMeetingActivity.this, getString(R.string.enter_contact_person), Toast.LENGTH_SHORT).show();
                } else if (address.equals("")) {
                    Toast.makeText(CreateMeetingActivity.this, getString(R.string.enter_address), Toast.LENGTH_SHORT).show();
                } else

                {
                    // pDialog.show();
                    progress.setVisibility(View.VISIBLE);
                    LoginModel model = sh.getLoginModel(getString(R.string.login_model));
                    String startedDate="",startedTime="",endDate="",endTime="";
                    if(alarm!=null)
                    { startedDate= alarm.getStartDate();startedTime= alarm.getStartTime();endDate= alarm.getEndDate();endTime= alarm.getEndTime();
                    }

                    createMeeting(model.getId(), purpose, descreption, customer, date, time, agenda, contactperson, address, startedDate,startedTime, endDate, endTime, "");
                }
            }
        });
    }

    AlarmModel alarm;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == RESULT_OK) {
            alarm = (AlarmModel) data.getSerializableExtra("ALARM");
            if (alarm != null)
                Log.e("***************", "on ativity**********************" + alarm.getStartTime());
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == 111) {
            return new DatePickerDialog(this, onDateSetListener, YY, MM, DD);
        } else if (id == 121) {
            return new TimePickerDialog(this, onTimeSetListener, H, M, true);
        }
        return onCreateDialog(id);
    }

    DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int y, int m, int d) {
            if ((m + 1) < 10)
                edtDate.setText(String.valueOf(y) + "-0" + String.valueOf(m + 1) + "-" + String.valueOf(d));
            else
                edtDate.setText(String.valueOf(y) + "-" + String.valueOf(m + 1) + "-" + String.valueOf(d));
        }
    };
    TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int h, int m) {
            timePicker.is24HourView();

        /*  if (h < 12 && h >= 0) {
                edtTime.setText(String.valueOf(h) + ":" + String.valueOf(m) + " " + "AM");
            } else {
                h -= 12;
                if (h == 0) {
                    h = 12;
                }
                edtTime.setText(String.valueOf(h) + ":" + String.valueOf(m) + " " + "PM");
            }*/
            edtTime.setText(String.valueOf(h) + ":" + String.valueOf(m));
        }
    };

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
        title.setText(getString(R.string.CreateMeeting));
    }

    public void getPurposeList() {
        Singleton.getInstance().getApi().getPurposeList("").enqueue(new Callback<ResponseMeta>() {
            @Override
            public void onResponse(Call<ResponseMeta> call, Response<ResponseMeta> response) {

                purposeList = response.body().getResponse();
            }

            @Override
            public void onFailure(Call<ResponseMeta> call, Throwable t) {

            }
        });
    }


    public void getCustomerList() {
        Singleton.getInstance().getApi().getCustomerList("").enqueue(new Callback<ResMetaCustomer>() {
            @Override
            public void onResponse(Call<ResMetaCustomer> call, Response<ResMetaCustomer> response) {

                listCustomer = response.body().getResponse();
            }

            @Override
            public void onFailure(Call<ResMetaCustomer> call, Throwable t) {

            }
        });
    }


    AlertDialog alertDialog;
    ArrayList<PurposeModel> purposeList = new ArrayList<>();
    PurposePopupAdaptor purposePopupAdaptor;
    private int popupId = 0;

    private void showPurposePopup() {
       /* PurposeModel m=new PurposeModel();
        m.setPurpose("Business Meeting in Noida");
        m.setId("0");

        purposeList.add(m);*/
        purposePopupAdaptor = new PurposePopupAdaptor(CreateMeetingActivity.this, purposeList);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        // ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.meeting_popup, null);
        final ListView listPurpose = dialogView.findViewById(R.id.listPurpose);
        //Button btnUpgrade = (Button) dialogView.findViewById(R.id.btnUpgrade);
        final SearchView editTextName = dialogView.findViewById(R.id.edt);
        editTextName.setQueryHint(getString(R.string.search_here));
        editTextName.setOnQueryTextListener(this);
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        alertDialog.show();
        popupId = 1;
        listPurpose.setAdapter(purposePopupAdaptor);

        listPurpose.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                PurposeModel obj = (PurposeModel) listPurpose.getAdapter().getItem(position);
                edtPurpose.setText(obj.getPurpose());
                alertDialog.dismiss();
            }
        });

    }

    ArrayList<CustomerModel> listCustomer;
    CustomerPopupAdaptor customerPopupAdaptor;

    private void showCustomerPopup() {
       /* PurposeModel m=new PurposeModel();
        m.setPurpose("Business Meeting in Noida");
        m.setId("0");

        purposeList.add(m);*/
        customerPopupAdaptor = new CustomerPopupAdaptor(CreateMeetingActivity.this, listCustomer);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        // ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.meeting_popup, null);
        final ListView listPurpose = dialogView.findViewById(R.id.listPurpose);
        TextView title = dialogView.findViewById(R.id.title);
        final SearchView editTextName = dialogView.findViewById(R.id.edt);
        editTextName.setQueryHint(getString(R.string.search_here));
        editTextName.setOnQueryTextListener(this);
        title.setText(getString(R.string.select_customer));
        //Button btnUpgrade = (Button) dialogView.findViewById(R.id.btnUpgrade);
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        popupId = 2;
        alertDialog.show();
        listPurpose.setAdapter(customerPopupAdaptor);

        listPurpose.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                CustomerModel obj = (CustomerModel) listPurpose.getAdapter().getItem(position);
                edtCustomer.setText(obj.getCustomer_name());
                customerid = obj.getId();
                alertDialog.dismiss();
            }
        });

    }

    String customerid;

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


            Log.e("start_date","**********start_date"+start_date);
            Log.e("end_date","**********end_date"+end_date);
            Log.e("start_time","**********start_time"+start_time);
            Log.e("end_time","**********end_time"+end_time);

        Singleton.getInstance().getApi().postMeeting(user_id, purpose
                , descreption
                , customerid
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

        ).enqueue(new Callback<MeetingModel>() {
            @Override
            public void onResponse(Call<MeetingModel> call, Response<MeetingModel> response) {
                progress.setVisibility(View.GONE);
                Toast.makeText(CreateMeetingActivity.this, getString(R.string.data_submited_successfully),
                        Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onFailure(Call<MeetingModel> call, Throwable t) {
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
            //Selecting the first object buffer.
            final Place place = places.get(0);
            CharSequence attributions = places.getAttributions();
            Toast.makeText(CreateMeetingActivity.this, place.getAddress(), Toast.LENGTH_SHORT).show();

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

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        s = s.toLowerCase();
        switch (popupId) {
            case 1:
                ArrayList<PurposeModel> newlist = new ArrayList<>();
                for (PurposeModel list : purposeList) {
                    String getPurpose = list.getPurpose().toLowerCase();

                    if (getPurpose.contains(s)) {
                        newlist.add(list);
                    }
                }
                purposePopupAdaptor.filter(newlist);
                break;

            case 2:
                ArrayList<CustomerModel> newlist1 = new ArrayList<>();
                for (CustomerModel list : listCustomer) {
                    String getCustomer = list.getCustomer_name().toLowerCase();
                    if (getCustomer.contains(s)) {
                        newlist1.add(list);
                    }
                }
                customerPopupAdaptor.filter(newlist1);
                break;
        }
        return false;
    }
}
