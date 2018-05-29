package com.example.lenovo.trackapp.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.lenovo.trackapp.R;
import com.example.lenovo.trackapp.actv.AddPreRequestActivity;
import com.example.lenovo.trackapp.adaptor.CurrencyAdaptor;
import com.example.lenovo.trackapp.adaptor.CustomerPopupAdaptor;
import com.example.lenovo.trackapp.adaptor.MeetingsAdaptor;
import com.example.lenovo.trackapp.adaptor.PlaceArrayAdapter;
import com.example.lenovo.trackapp.adaptor.RequestTypesAdaptor;
import com.example.lenovo.trackapp.model.CurrencyModel;
import com.example.lenovo.trackapp.model.CustomerModel;
import com.example.lenovo.trackapp.model.LoginModel;
import com.example.lenovo.trackapp.model.MeetingModel;
import com.example.lenovo.trackapp.model.RequestTypeModel;
import com.example.lenovo.trackapp.model.ResMetaCurrency;
import com.example.lenovo.trackapp.model.ResMetaCustomer;
import com.example.lenovo.trackapp.model.ResMetaMeeting;
import com.example.lenovo.trackapp.model.ResMetaReqTypes;
import com.example.lenovo.trackapp.util.Shprefrences;
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
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewExpenseActivity extends AppCompatActivity implements  GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks,SearchView.OnQueryTextListener {
    private static final String TAG = "CreateMeetingActivity";
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private AutoCompleteTextView edtAddress;
    private GoogleApiClient mGoogleApiClient;
    private PlaceArrayAdapter mPlaceArrayAdapter;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));
    private static final int SELECT_PHOTO = 200;
    EditText meeting, date, customername, getDate, amount, comments, time,currency;
    private static final int CAMERA_REQUEST = 1888;
    ImageView imageView;
    TextView textView;
    public static String imgUrl;
    Button submit;
    Shprefrences sh;
    ProgressBar progress;
    private AutoCompleteTextView location;
    ArrayList<CurrencyModel> currencyList = new ArrayList<>();
    ArrayList<MeetingModel> meetingList = new ArrayList<>();
    ArrayList<RequestTypeModel> requestTyoesList = new ArrayList<>();
    ListView listTypes;
    int H, M;
    Calendar calendar;
    int DD, MM, YY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_expense);
        getSupportActionBar().setTitle("Create Expanse");
        meeting = findViewById(R.id.edt_meeting);
        amount = (EditText) findViewById(R.id.edt_amount);
        date = (EditText) findViewById(R.id.edt_date);
        location = (AutoCompleteTextView) findViewById(R.id.edt_location);
        time = (EditText) findViewById(R.id.edt_time);
        imageView = (ImageView) findViewById(R.id.imz_loadreceipt);
        customername = (EditText) findViewById(R.id.edt_vendor);
        textView = (TextView) findViewById(R.id.btn_load);
        comments = (EditText) findViewById(R.id.edt_comment);
        progress = findViewById(R.id.progress);
        listTypes = findViewById(R.id.listTypes);
        currency=(EditText)findViewById(R.id.edt_Currency) ;
        getDate = (EditText) findViewById(R.id.edt_date1);
        submit = findViewById(R.id.btnSubmit);
        if (imgUrl != null && !imgUrl.equalsIgnoreCase(""))
            Picasso.get().load(imgUrl).into(imageView);
        sh=new Shprefrences(this);
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
        String date1 = df.format(Calendar.getInstance().getTime());
        getDate.setText("Created:" + date1);
        mGoogleApiClient = new GoogleApiClient.Builder(NewExpenseActivity.this)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, GOOGLE_API_CLIENT_ID, this)
                .addConnectionCallbacks(this)
                .build();
        location.setOnItemClickListener(mAutocompleteClickListener);
        mPlaceArrayAdapter = new PlaceArrayAdapter(
                this, android.R.layout.simple_list_item_1,
                BOUNDS_MOUNTAIN_VIEW, null);
        location.setAdapter(mPlaceArrayAdapter);
        location.setThreshold(1);
        progress.setVisibility(View.VISIBLE);
        getReqestTypes();
        getMeetingsList();
        getCurrencyList();
        getCustomerList();
        calendar = Calendar.getInstance();
        DD = calendar.get(Calendar.DAY_OF_MONTH);
        MM = calendar.get(Calendar.MONTH);
        YY = calendar.get(Calendar.YEAR);
        date.setText(String.valueOf(YY) + "-" + String.valueOf(MM + 1) + "-" + String.valueOf(DD));
        H = calendar.get(Calendar.HOUR_OF_DAY);
        M = calendar.get(Calendar.MINUTE);
        if (H < 12 && H >= 0) {
            time.setText(String.valueOf(H) + ":" + String.valueOf(M) + " " + "AM");
        } else {
            H -= 12;
            if (H == 0) {
                H = 12;
            }
            time.setText(String.valueOf(H) + ":" + String.valueOf(M) + " " + "PM");
        }
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                showDialog(121);
            }
        });
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                showDialog(111);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectmeeting = meeting.getText().toString();
                String amnt = amount.getText().toString();

                String dt = date.getText().toString();
                String loc = location.getText().toString();
                String vndr = customername.getText().toString();
                String cmnt = comments.getText().toString();
                String t = time.getText().toString();
                String createddate = getDate.getText().toString();
                if (selectmeeting.equals("")) {
                    Toast.makeText(NewExpenseActivity.this, "Select Meeting", Toast.LENGTH_SHORT).show();
                    return;
                } else if (amnt.equals("")) {
                    Toast.makeText(NewExpenseActivity.this, "Enter Amount", Toast.LENGTH_SHORT).show();
                    return;
                }  else if (t.equals("")) {
                    Toast.makeText(NewExpenseActivity.this, "enter time", Toast.LENGTH_SHORT).show();
                    return;
                } else if (dt.equals("")) {
                    Toast.makeText(NewExpenseActivity.this, "Enter date", Toast.LENGTH_SHORT).show();
                    return;
                } else if (loc.equals("")) {
                    Toast.makeText(NewExpenseActivity.this, "Enter location", Toast.LENGTH_SHORT).show();
                    return;
                } else if (vndr.equals("")) {
                    Toast.makeText(NewExpenseActivity.this, "Enter Vender Name", Toast.LENGTH_SHORT).show();
                    return;
                } else if (cmnt.equals("")) {
                    Toast.makeText(NewExpenseActivity.this, "Enter Your Comment", Toast.LENGTH_SHORT).show();
                    return;
                } else {

                    Toast.makeText(NewExpenseActivity.this, "saved", Toast.LENGTH_SHORT).show();
                }
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
        meeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMeetings();
            }
        });
        currency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCurrencyList();
            }
        });
        customername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomerPopup();
            }
        });
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
    public void getReqestTypes() {
        LoginModel model = sh.getLoginModel("LOGIN_MODEL");
        Singleton.getInstance().getApi().getRequestTypes(model.getId()).enqueue(new Callback<ResMetaReqTypes>() {
            @Override
            public void onResponse(Call<ResMetaReqTypes> call, Response<ResMetaReqTypes> response) {
                requestTyoesList = response.body().getResponse();
                RequestTypesAdaptor adapto = new RequestTypesAdaptor(NewExpenseActivity.this, requestTyoesList);
                listTypes.setAdapter(adapto);
                progress.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(Call<ResMetaReqTypes> call, Throwable throwable) {
                progress.setVisibility(View.GONE);
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



    private void selectImage() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(NewExpenseActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                } else if (options[item].equals("Choose from Gallery")) {
                    openGallery();
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void openGallery() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
        } else if (requestCode == SELECT_PHOTO) {
            if (resultCode == RESULT_OK) {
                Uri selectedImage = data.getData();
                if (selectedImage != null) {
                    imageView.setImageURI(selectedImage);
                }
            }
        }
    }



    android.app.AlertDialog alertDialog;
    MeetingsAdaptor adaptor;
    private int popupId = 0;

    private void showMeetings() {

        adaptor = new MeetingsAdaptor(NewExpenseActivity.this, meetingList);

        android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(this);
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
                meeting.setText(obj.getDescreption());
                alertDialog.dismiss();
            }
        });

    }
    CurrencyAdaptor currencyAdaptor;
    private void showCurrencyList() {
        currencyAdaptor = new CurrencyAdaptor(NewExpenseActivity.this, currencyList);

        android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(this);
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
                currency.setText(obj.getCurrency_name());
                alertDialog.dismiss();
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

    ArrayList<CustomerModel>listCustomer;
    CustomerPopupAdaptor customerPopupAdaptor;
    private void showCustomerPopup() {
       /* PurposeModel m=new PurposeModel();
        m.setPurpose("Business Meeting in Noida");
        m.setId("0");

        purposeList.add(m);*/
        customerPopupAdaptor = new CustomerPopupAdaptor(NewExpenseActivity.this, listCustomer);

        android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(this);
        // ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.meeting_popup, null);
        final ListView listPurpose = dialogView.findViewById(R.id.listPurpose);
        TextView title= dialogView.findViewById(R.id.title);
        final SearchView editTextName = dialogView.findViewById(R.id.edt);
        editTextName.setQueryHint("Search Here");
        editTextName.setOnQueryTextListener(this);
        title.setText("Select Customer");
        //Button btnUpgrade = (Button) dialogView.findViewById(R.id.btnUpgrade);
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        popupId = 3;
        alertDialog.show();
        listPurpose.setAdapter(customerPopupAdaptor);

        listPurpose.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                CustomerModel obj = (CustomerModel) listPurpose.getAdapter().getItem(position);
                customername.setText(obj.getCustomer_name());
                alertDialog.dismiss();
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
            Toast.makeText(NewExpenseActivity.this,place.getAddress(),Toast.LENGTH_SHORT).show();

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
                ArrayList<CustomerModel> newlist2 = new ArrayList<>();
                for (CustomerModel list : listCustomer) {
                    String getCustomer = list.getCustomer_name().toLowerCase();
                    if (getCustomer.contains(s)) {
                        newlist2.add(list);
                    }
                }
                customerPopupAdaptor.filter(newlist2);
                break;
              }
        return true;
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
            date.setText(String.valueOf(y) + "-" + String.valueOf(m + 1) + "-" + String.valueOf(d));
        }
    };
    TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int h, int m) {
            if (h < 12 && h >= 0) {
                time.setText(String.valueOf(h) + ":" + String.valueOf(m) + " " + "AM");
            } else {
                h -= 12;
                if (h == 0) {
                    h = 12;
                }
                time.setText(String.valueOf(h) + ":" + String.valueOf(m) + " " + "PM");
            }
        }
    };
}
