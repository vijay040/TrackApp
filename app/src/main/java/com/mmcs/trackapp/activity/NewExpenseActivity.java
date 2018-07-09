package com.mmcs.trackapp.activity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.mmcs.trackapp.R;
import com.mmcs.trackapp.adaptor.CurrencyAdaptor;
import com.mmcs.trackapp.adaptor.CustomerPopupAdaptor;
import com.mmcs.trackapp.adaptor.ExpenseTypesAdaptor;
import com.mmcs.trackapp.adaptor.MeetingsAdaptor;
import com.mmcs.trackapp.adaptor.PlaceArrayAdapter;
import com.mmcs.trackapp.adaptor.RequestTypesAdaptor;
import com.mmcs.trackapp.model.CurrencyModel;
import com.mmcs.trackapp.model.CustomerModel;
import com.mmcs.trackapp.model.LoginModel;
import com.mmcs.trackapp.model.MeetingModel;
import com.mmcs.trackapp.model.RequestTypeModel;
import com.mmcs.trackapp.model.ResMetaCurrency;
import com.mmcs.trackapp.model.ResMetaCustomer;
import com.mmcs.trackapp.model.ResMetaMeeting;
import com.mmcs.trackapp.model.ResMetaReqTypes;
import com.mmcs.trackapp.util.Shprefrences;
import com.mmcs.trackapp.util.Singleton;
import com.mmcs.trackapp.util.Util;
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

import java.io.FileNotFoundException;
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
    TextView image_path,edRequestTypes;
    final int MY_PERMISSIONS_REQUEST_WRITE=103;
    String createddate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_expense);
        meeting = findViewById(R.id.edt_meeting);
        amount = (EditText) findViewById(R.id.edt_amount);
        imageView = (ImageView) findViewById(R.id.imageView);
        attachement = (Button) findViewById(R.id.btAttchment);
        comments = (EditText) findViewById(R.id.edt_comment);
        image_path = findViewById(R.id.image_path);
        progress = findViewById(R.id.progress);
        edRequestTypes=findViewById(R.id.edRequestTypes);
        currency = (EditText) findViewById(R.id.edt_Currency);
        getDate = (EditText) findViewById(R.id.edt_date1);
        submit = findViewById(R.id.btnSubmit);
        //getSupportActionBar().setTitle("Create Expense");
        if (imgUrl != null && !imgUrl.equalsIgnoreCase(""))
            Picasso.get().load(imgUrl).into(imageView);
        sh = new Shprefrences(this);
        DateFormat df = new SimpleDateFormat(getString(R.string.date_formate));
        final String createddate = df.format(Calendar.getInstance().getTime());
        getDate.setText(getString(R.string.expense_created_on) + createddate);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE);
        }
        progress.setVisibility(View.VISIBLE);
        getReqestTypes();
        getMeetingsList();
        getCurrencyList();
        edRequestTypes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRequests();
            }
        });
        back();
       setTitle();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectmeeting = meeting.getText().toString();
                String amnt = amount.getText().toString();
                String currenc = currency.getText().toString();
                String expensetype=edRequestTypes.getText().toString();
                String cmnt = comments.getText().toString();
                String totalamount = amnt + currenc;
                if (selectmeeting.equals("")) {
                    Toast.makeText(NewExpenseActivity.this, getString(R.string.select_meeting), Toast.LENGTH_SHORT).show();
                    return;
                } else if (amnt.equals("")) {
                    Toast.makeText(NewExpenseActivity.this, getString(R.string.enter_amount), Toast.LENGTH_SHORT).show();
                    return;
                } else if (currenc.equals("")) {
                    Toast.makeText(NewExpenseActivity.this, getString(R.string.select_currency), Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (expensetype.equals("")) {
                    Toast.makeText(NewExpenseActivity.this, getString(R.string.select_expensetype), Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (cmnt.equals("")) {
                    Toast.makeText(NewExpenseActivity.this, getString(R.string.enter_your_comment), Toast.LENGTH_SHORT).show();
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
        LoginModel model = sh.getLoginModel(getString(R.string.login_model));
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
        LoginModel model = sh.getLoginModel(getString(R.string.login_model));
        Singleton.getInstance().getApi().getExpenseTypes(model.getId()).enqueue(new Callback<ResMetaReqTypes>() {
            @Override
            public void onResponse(Call<ResMetaReqTypes> call, Response<ResMetaReqTypes> response) {
                requestTyoesList = response.body().getResponse();


                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResMetaReqTypes> call, Throwable throwable) {
                progress.setVisibility(View.GONE);
            }
        });
    }

    private void getCurrencyList() {
        LoginModel model = sh.getLoginModel(getString(R.string.login_model));
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
    Uri fileUri;

    private void selectImage() {
        final CharSequence[] options = {getString(R.string.take_photo), getString(R.string.choose_from_gallery),getString(R.string.cancel)};
        AlertDialog.Builder builder = new AlertDialog.Builder(NewExpenseActivity.this);
        builder.setTitle(getString(R.string.add_photo));
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals(getString(R.string.take_photo))) {
                    /*Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);*/
                    String fileName = System.currentTimeMillis()+".jpg";
                    ContentValues values = new ContentValues();
                    values.put(MediaStore.Images.Media.TITLE, fileName);
                    fileUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                    startActivityForResult(intent, CAMERA_REQUEST);
                } else if (options[item].equals(getString(R.string.choose_from_gallery))) {
                    openGallery();
                } else if (options[item].equals(getString(R.string.cancel))) {
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
           /* Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
            imageImagePath = data.getData().getPath();
            image_path.setText(imageImagePath);*/
            try
            {
                String  photoPath = getPath(fileUri);

                System.out.println("Image Path : " + photoPath);
                image_path.setText(photoPath);
                Bitmap b = decodeUri(fileUri);
                imageView.setImageBitmap(b);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

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
        editTextName.setQueryHint(getString(R.string.search_here));
        editTextName.setOnQueryTextListener(this);
        //Button btnUpgrade = (Button) dialogView.findViewById(R.id.btnUpgrade);
        title.setText(getString(R.string.selected_created_meetings));
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


String requestTypeId;
    private void showRequests() {
        ExpenseTypesAdaptor adapto = new ExpenseTypesAdaptor(NewExpenseActivity.this, requestTyoesList);
        android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(this);
        // ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.meeting_popup, null);
        final ListView listPurpose = dialogView.findViewById(R.id.listPurpose);
        TextView title = dialogView.findViewById(R.id.title);
        final SearchView editTextName = dialogView.findViewById(R.id.edt);
        editTextName.setQueryHint(getString(R.string.search_here));
        editTextName.setOnQueryTextListener(this);
        //Button btnUpgrade = (Button) dialogView.findViewById(R.id.btnUpgrade);
        title.setText(getString(R.string.selected_expense_types));
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        popupId = 1;
        alertDialog.show();
        listPurpose.setAdapter(adapto);
        listPurpose.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                RequestTypeModel obj = (RequestTypeModel) listPurpose.getAdapter().getItem(position);
                Log.e("selected**", "" + obj.getRequest_type());
                edRequestTypes.setText(obj.getRequest_type());
                requestTypeId = obj.getId();
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
        editTextName.setQueryHint(getString(R.string.search_here));
        editTextName.setOnQueryTextListener(this);
        title.setText(getString(R.string.select_currency));
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

    private void postExpanse(String amnt, String createddate, String cmnt) {
        LoginModel model = sh.getLoginModel(getString(R.string.login_model));
        String userid = model.getId();
        Singleton.getInstance().getApi().postExpanse(userid, meetingId, amnt, requestTypeId, createddate, cmnt).enqueue(new Callback<ResMetaMeeting>() {
            @Override
            public void onResponse(Call<ResMetaMeeting> call, Response<ResMetaMeeting> response) {
                Toast.makeText(NewExpenseActivity.this, getString(R.string.expanse_posted_successfully), Toast.LENGTH_SHORT).show();
                progress.setVisibility(View.GONE);
                finish();
            }

            @Override
            public void onFailure(Call<ResMetaMeeting> call, Throwable t) {
                progress.setVisibility(View.GONE);
                Toast.makeText(NewExpenseActivity.this, getString(R.string.sorry_try_again), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void setTitle()
    {
        TextView title= (TextView) findViewById(R.id.title);
        title.setText(getString(R.string.create_expense));
    }
    private Bitmap decodeUri(Uri selectedImage) throws FileNotFoundException
    {
        BitmapFactory.Options o = new BitmapFactory.Options();

        o.inJustDecodeBounds = true;

        BitmapFactory.decodeStream(getContentResolver()
                .openInputStream(selectedImage), null, o);

        final int REQUIRED_SIZE = 72;

        int width_tmp = o.outWidth, height_tmp = o.outHeight;

        int scale = 1;

        while (true)
        {
            if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
            {
                break;
            }
            width_tmp /= 2;

            height_tmp /= 2;

            scale *= 2;
        }

        BitmapFactory.Options o2 = new BitmapFactory.Options();

        o2.inSampleSize = scale;

        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver()
                .openInputStream(selectedImage), null, o2);

        return bitmap;
    }

    @SuppressWarnings("deprecation")
    private String getPath(Uri selectedImaeUri)
    {
        String[] projection = { MediaStore.Images.Media.DATA };

        Cursor cursor = managedQuery(selectedImaeUri, projection, null, null,
                null);

        if (cursor != null)
        {
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

            return cursor.getString(columnIndex);
        }

        return selectedImaeUri.getPath();
    }


}
