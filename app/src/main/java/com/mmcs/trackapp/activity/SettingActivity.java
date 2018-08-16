package com.mmcs.trackapp.activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.mmcs.trackapp.R;
import com.mmcs.trackapp.adaptor.CurrencyAdaptor;
import com.mmcs.trackapp.adaptor.PlaceArrayAdapter;
import com.mmcs.trackapp.model.CurrencyModel;
import com.mmcs.trackapp.model.LoginModel;
import com.mmcs.trackapp.model.ResMetaCurrency;
import com.mmcs.trackapp.model.ResMetaMeeting;
import com.mmcs.trackapp.model.UploadImageModel;
import com.mmcs.trackapp.model.UploadImageResMeta;
import com.mmcs.trackapp.util.CircleTransform;
import com.mmcs.trackapp.util.Shprefrences;
import com.mmcs.trackapp.util.Singleton;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.security.AccessController.getContext;

public class SettingActivity extends AppCompatActivity implements SearchView.OnQueryTextListener ,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks{
    EditText edt_txt_first_name, edt_txt_last_name, edt_txt_email_id, edt_txt_role, edt_txt_manager, edt_txt_numberformate, edt_txt_position, edt_txt_joiningdate, edt_txt_department, edt_txt_conf_personal_number, edt_txt_currency, edt_txt_dateformate, edt_txt_language;
    Shprefrences sh;
    TextView edt_txt_password_professional, text_edit;
    ArrayList<CurrencyModel> currencyList = new ArrayList<>();
    LoginModel model;
    ImageView imgProfile;
    private static final int SELECT_PHOTO = 200;
    private static final int CAMERA_REQUEST = 1888;

    private static final int GOOGLE_API_CLIENT_ID = 0;
    private AutoCompleteTextView edtAddress;
    private GoogleApiClient mGoogleApiClient;
    private PlaceArrayAdapter mPlaceArrayAdapter;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        //getSupportActionBar().setTitle("Settings");
        sh = new Shprefrences(this);
        edt_txt_first_name = findViewById(R.id.edt_txt_first_name);
        edt_txt_last_name = findViewById(R.id.edt_txt_last_name);
        edt_txt_email_id = findViewById(R.id.edt_txt_email_id);
        edt_txt_conf_personal_number = findViewById(R.id.edt_txt_conf_personal_number);
        edt_txt_password_professional = findViewById(R.id.edt_txt_password_professional);
        edtAddress = findViewById(R.id.edtAddress);
        edt_txt_position = findViewById(R.id.edt_txt_position);
        edt_txt_manager = findViewById(R.id.edt_txt_manager);
        edt_txt_role = findViewById(R.id.edt_txt_role);
        edt_txt_numberformate = findViewById(R.id.edt_txt_numberformate);
        edt_txt_department = findViewById(R.id.edt_txt_department);
        edt_txt_joiningdate = findViewById(R.id.edt_txt_joiningdate);
        edt_txt_currency = findViewById(R.id.edt_txt_currency);
        edt_txt_dateformate = findViewById(R.id.edt_txt_dateformate);
        edt_txt_language = findViewById(R.id.edt_txt_language);
        imgProfile = findViewById(R.id.imgProfile);
        text_edit = findViewById(R.id.text_edit);

        mGoogleApiClient = new GoogleApiClient.Builder(SettingActivity.this)
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

        text_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        model = sh.getLoginModel(getString(R.string.login_model));
        edt_txt_first_name.setText(model.getDisplay_name() + "");
        edt_txt_last_name.setText(model.getUser_name() + "");
        edt_txt_email_id.setText(model.getEmail() + "");
        edt_txt_manager.setText(model.getReporting_person() + "");
        edt_txt_role.setText(model.getRole_id() + "");
        edt_txt_department.setText(model.getDepartment() + "");
        edt_txt_joiningdate.setText(model.getJoining_date() + "");
        edt_txt_conf_personal_number.setText(model.getMobile_number() + "");
        //edt_txt_password_professional.setText(model.get() + "");
        model = sh.getLoginModel(getString(R.string.login_model));
        if(model.getImage()!=null)
            Picasso.get().load(model.getImage()).transform(new CircleTransform()).placeholder(R.drawable.ic_userlogin).into(imgProfile);
       // txtName.setText(model.getDisplay_name());

        getCurrencyList();
        back();
        setTitle();
        edt_txt_dateformate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateFormate();
            }
        });
        edt_txt_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLanguage();
            }
        });
        edt_txt_currency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCurrencyList();
            }
        });
    }

    private void getCurrencyList() {
        LoginModel model = sh.getLoginModel(getString(R.string.login_model));
        Singleton.getInstance().getApi().getCurrencyList(model.getId()).enqueue(new Callback<ResMetaCurrency>() {
            @Override
            public void onResponse(Call<ResMetaCurrency> call, Response<ResMetaCurrency> response) {
                currencyList = response.body().getResponse();
            }

            @Override
            public void onFailure(Call<ResMetaCurrency> call, Throwable t) {
            }
        });
    }

    android.app.AlertDialog alertDialog;
    CurrencyAdaptor currencyAdaptor;

    private void showCurrencyList() {
        currencyAdaptor = new CurrencyAdaptor(SettingActivity.this, currencyList);
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
        alertDialog.show();
        listPurpose.setAdapter(currencyAdaptor);
        listPurpose.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CurrencyModel obj = (CurrencyModel) listPurpose.getAdapter().getItem(position);
                edt_txt_currency.setText(obj.getCurrency_name());
                alertDialog.dismiss();
            }
        });
    }

    private void showLanguage() {
        android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.activity_language, null);
        final ListView listlanguage = dialogView.findViewById(R.id.listview);
        String[] Language = new String[]{"Indonesian", "English", "Thai", "Vietenam"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, Language);
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        alertDialog.show();
        listlanguage.setAdapter(adapter);
        listlanguage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemValue = (String) listlanguage.getItemAtPosition(position);
                edt_txt_language.setText(itemValue);
                alertDialog.dismiss();
            }
        });
    }

    private void showDateFormate() {
        android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.activity_language, null);
        final ListView listDate = dialogView.findViewById(R.id.listview);
        String[] Date = new String[]{"DD/MM/YY", "MM/DD/YY", "YY/MM/DD"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, Date);
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        alertDialog.show();
        listDate.setAdapter(adapter);
        listDate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemValue = (String) listDate.getItemAtPosition(position);
                edt_txt_dateformate.setText(itemValue);
                alertDialog.dismiss();
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
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
    public boolean onQueryTextChange(String s) {
        s = s.toLowerCase();
        ArrayList<CurrencyModel> newlist = new ArrayList<>();
        for (CurrencyModel list : currencyList) {
            String getCurrency = list.getCurrency_name().toLowerCase();
            if (getCurrency.contains(s)) {
                newlist.add(list);
            }
        }
        currencyAdaptor.filter(newlist);
        return true;
    }

    Uri fileUri;

    private void selectImage() {
        final CharSequence[] options = {getString(R.string.take_photo), getString(R.string.choose_from_gallery), getString(R.string.cancel)};
        AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
        builder.setTitle(getString(R.string.add_photo));
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals(getString(R.string.take_photo))) {
                    String fileName = System.currentTimeMillis() + ".jpg";
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
            try {
                imageImagePath = getPath(fileUri);
                File file=new File(imageImagePath);
                resize(file,"");
                Picasso.get().load(fileUri).transform(new CircleTransform()).placeholder(R.drawable.ic_userlogin).into(imgProfile);
                updateUserProfile(imageImagePath);

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (requestCode == SELECT_PHOTO) {
            if (resultCode == RESULT_OK) {
                Uri selectedImage = data.getData();
                if (selectedImage != null) {
                    imageImagePath = getPath(selectedImage);
                    File file=new File(imageImagePath);
                    resize(file,"");
                    Picasso.get().load(file).transform(new CircleTransform()).placeholder(R.drawable.ic_userlogin).resize(100,100).into(imgProfile);
                    updateUserProfile(imageImagePath);
                }
            }
        }
    }

    private Bitmap decodeUri(Uri selectedImage) throws FileNotFoundException {
        BitmapFactory.Options o = new BitmapFactory.Options();

        o.inJustDecodeBounds = true;

        BitmapFactory.decodeStream(getContentResolver()
                .openInputStream(selectedImage), null, o);

        final int REQUIRED_SIZE = 72;

        int width_tmp = o.outWidth, height_tmp = o.outHeight;

        int scale = 1;

        while (true) {
            if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE) {
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
    private String getPath(Uri selectedImaeUri) {
        String[] projection = {MediaStore.Images.Media.DATA};

        Cursor cursor = managedQuery(selectedImaeUri, projection, null, null,
                null);

        if (cursor != null) {
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

            return cursor.getString(columnIndex);
        }

        return selectedImaeUri.getPath();
    }

    private void setTitle() {
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(getString(R.string.settings));
    }

    public void updateUserProfile(String fileUrl) {
        RequestBody imgFile = null;
        File imagPh = new File(fileUrl);
        Log.e("***********", "*************" + imagPh.getAbsolutePath());
        if (imagPh != null)
            imgFile = RequestBody.create(MediaType.parse("image/*"), imagPh);
        RequestBody requestId = RequestBody.create(MediaType.parse("text/plain"), model.getId());
        Singleton.getInstance().getApi().updateUserProfile(requestId, imgFile).enqueue(new Callback<UploadImageResMeta>() {
            @Override
            public void onResponse(Call<UploadImageResMeta> call, Response<UploadImageResMeta> response) {
                if (response.body().getCode().equalsIgnoreCase("200")) {
                    UploadImageModel up = response.body().getData();
                    model.setImage(up.getImage());
                    sh.setLoginModel(getString(R.string.login_model), model);
                   // Picasso.get().load(up.getImage()).transform(new CircleTransform()).placeholder(R.drawable.ic_userlogin).into(imgProfile);
                }
            }

            @Override
            public void onFailure(Call<UploadImageResMeta> call, Throwable t) {

            }
        });
    }



    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final PlaceArrayAdapter.PlaceAutocomplete item = mPlaceArrayAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);

        }
    };
    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                        places.getStatus().toString();
                return;
            }
            //Selecting the first object buffer.
            final Place place = places.get(0);
            CharSequence attributions = places.getAttributions();
            Toast.makeText(SettingActivity.this, place.getAddress(), Toast.LENGTH_SHORT).show();

        }
    };

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mPlaceArrayAdapter.setGoogleApiClient(mGoogleApiClient);
    }

    @Override
    public void onConnectionSuspended(int i) {
        mPlaceArrayAdapter.setGoogleApiClient(null);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        Toast.makeText(this,
                "Google Places API connection failed with error code:" +
                        connectionResult.getErrorCode(),
                Toast.LENGTH_LONG).show();
    }


    BitmapFactory.Options bmOptions;
    Bitmap bitmap;
    public void resize(File file, String benchMark) {
        try {
            bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = false;
            bmOptions.inPreferredConfig = Bitmap.Config.RGB_565;
            bmOptions.inDither = true;
            bitmap = BitmapFactory.decodeFile(imageImagePath, bmOptions);
            int w = bitmap.getWidth();
            int h = bitmap.getHeight();
            Log.e("width & Height", "width " + bitmap.getWidth());
            if (bitmap.getWidth() > 1200) {
                w = (bitmap.getWidth() * 30 )/ 100;
                h =(bitmap.getHeight() * 30) / 100;
            }

            Log.e("width & Height", "width " + w + " height " + h);
            bitmap = Bitmap.createScaledBitmap(bitmap, w, h, true);

            /*Canvas c = new Canvas(bitmap);
            Paint p = new Paint();
            p.setColor(Color.BLUE);
            p.setStyle(Paint.Style.FILL);
            p.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            // paint.setColor(Color.BLACK);
            p.setTextSize(30);
            c.drawText(benchMark, 20, 40, p);*/

            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, bytes);
            try {
                Log.e("Compressing", "Compressing");
                FileOutputStream fo = new FileOutputStream(file);
                fo.write(bytes.toByteArray());
                fo.close();
            } catch (Exception e) {
                Log.e("Exception", "Image Resizing" + e.getMessage());
            }
        } catch (
                Exception e
                ) {
            Log.e("Exception", "Exception in resizing image");
        }
    }

}
