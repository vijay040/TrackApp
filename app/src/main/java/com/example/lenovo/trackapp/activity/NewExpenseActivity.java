package com.example.lenovo.trackapp.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
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
import com.squareup.picasso.Picasso;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

public class NewExpenseActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private static final String TAG = "CreateMeetingActivity";

    private static final int SELECT_PHOTO = 200;
    EditText meeting, getDate, amount, comments, currency;
    private static final int CAMERA_REQUEST = 1888;
    ImageView imageView;
    public static String imgUrl;
    Button submit, attachement;
    Shprefrences sh;
    ProgressBar progress;
    ArrayList<CurrencyModel> currencyList = new ArrayList<>();
    ArrayList<MeetingModel> meetingList = new ArrayList<>();
    ArrayList<RequestTypeModel> requestTyoesList = new ArrayList<>();
    ListView listTypes;
    TextView image_path;
    String createddate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_expense);
        getSupportActionBar().setTitle("Create Expanse");
        meeting = findViewById(R.id.edt_meeting);
        amount = (EditText) findViewById(R.id.edt_amount);
        imageView = (ImageView) findViewById(R.id.imageView);
        attachement = (Button) findViewById(R.id.btAttchment);
        comments = (EditText) findViewById(R.id.edt_comment);
        image_path = findViewById(R.id.image_path);
        progress = findViewById(R.id.progress);
        listTypes = findViewById(R.id.listTypes);
        currency = (EditText) findViewById(R.id.edt_Currency);
        getDate = (EditText) findViewById(R.id.edt_date1);
        submit = findViewById(R.id.btnSubmit);
        getSupportActionBar().setTitle("Create Expense");
        if (imgUrl != null && !imgUrl.equalsIgnoreCase(""))
            Picasso.get().load(imgUrl).into(imageView);
        sh = new Shprefrences(this);
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
        final String createddate = df.format(Calendar.getInstance().getTime());
        getDate.setText("Created On:" + createddate);
        progress.setVisibility(View.VISIBLE);
        getReqestTypes();
        getMeetingsList();
        getCurrencyList();

      /*  listTypes.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
*/
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectmeeting = meeting.getText().toString();
                String amnt = amount.getText().toString();
                String currenc = currency.getText().toString();
                String cmnt = comments.getText().toString();
                String totalamount = amnt + currenc;
                if (selectmeeting.equals("")) {
                    Toast.makeText(NewExpenseActivity.this, "Select Meeting", Toast.LENGTH_SHORT).show();
                    return;
                } else if (amnt.equals("")) {
                    Toast.makeText(NewExpenseActivity.this, "Enter Amount", Toast.LENGTH_SHORT).show();
                    return;
                } else if (currenc.equals("")) {
                    Toast.makeText(NewExpenseActivity.this, "Select Currency", Toast.LENGTH_SHORT).show();
                    return;
                } else if (cmnt.equals("")) {
                    Toast.makeText(NewExpenseActivity.this, "Enter Your Comment", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    progress.setVisibility(View.VISIBLE);
                    postExpanse(totalamount, createddate, cmnt);
                }
            }
        });
        attachement.setOnClickListener(new View.OnClickListener() {
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
                Util.setListViewHeightBasedOnItems(listTypes);
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

    String imageImagePath = "";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
            imageImagePath = data.getData().getPath();
            image_path.setText(imageImagePath);
        } else if (requestCode == SELECT_PHOTO) {
            if (resultCode == RESULT_OK) {
                Uri selectedImage = data.getData();
                if (selectedImage != null) {
                    imageView.setImageURI(selectedImage);
                    imageImagePath = selectedImage.getPath();
                    image_path.setText(imageImagePath);
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
                meetingId = obj.getId();
                alertDialog.dismiss();
            }
        });
    }

    CurrencyAdaptor currencyAdaptor;
    String meetingId;

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

        }
        return true;
    }



    private void postExpanse(String amnt, String createddate,  String cmnt) {

        LoginModel model = sh.getLoginModel("LOGIN_MODEL");
        String userid = model.getId();
        ArrayList<RequestTypeModel>listtype=new ArrayList<>();
        for(RequestTypeModel list :requestTyoesList)
        {
            if(list.isSelected())
                listtype.add(list);
        }

        Singleton.getInstance().getApi().postExpanse(userid, meetingId, amnt,  listtype, createddate, cmnt).enqueue(new Callback<ResMetaMeeting>() {
            @Override
            public void onResponse(Call<ResMetaMeeting> call, Response<ResMetaMeeting> response) {
                Toast.makeText(NewExpenseActivity.this, "Expanse posted successfully!", Toast.LENGTH_SHORT).show();
                progress.setVisibility(View.GONE);
                finish();
            }

            @Override
            public void onFailure(Call<ResMetaMeeting> call, Throwable t) {
                progress.setVisibility(View.GONE);
                Toast.makeText(NewExpenseActivity.this, "Sorry!Try Again!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
