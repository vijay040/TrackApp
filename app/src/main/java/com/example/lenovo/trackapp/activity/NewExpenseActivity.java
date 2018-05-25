package com.example.lenovo.trackapp.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.trackapp.R;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NewExpenseActivity extends AppCompatActivity {
    private static final int SELECT_PHOTO = 200;
    EditText meeting,date, location, vender, expensetype, getDate, amount,comments;
    private static final int CAMERA_REQUEST = 1888;
    Bitmap bmp;
    ImageView imageView;
    TextView textView;
    static String value;
    public static String imgUrl;
    private static final String IMAGE_DIRECTORY = "/demonuts";
    private String TAG;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_expense);
        getSupportActionBar().setTitle("Create Expanse");
        meeting=findViewById(R.id.edt_meeting);
        amount = (EditText) findViewById(R.id.edt_amount);
        date = (EditText) findViewById(R.id.edt_date);
        location = (EditText) findViewById(R.id.edt_location);
        imageView = (ImageView) findViewById(R.id.imz_loadreceipt);
        vender = (EditText) findViewById(R.id.edt_vendor);
        textView = (TextView) findViewById(R.id.btn_load);
        comments = (EditText) findViewById(R.id.edt_comment);
        expensetype = (EditText) findViewById(R.id.edt_Expensetype);
        getDate = (EditText) findViewById(R.id.edt_date1);
        submit=findViewById(R.id.btnSubmit);
        if(imgUrl != null && !imgUrl.equalsIgnoreCase(""))
            Picasso.get().load(imgUrl).into(imageView);
            Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getString("amount");
        }
        amount.setText(value);
        expensetype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expensetypePopup();
            }
        });
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
        String date1 = df.format(Calendar.getInstance().getTime());
        getDate.setText("Created:" + date1);
        amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewExpenseActivity.this, CurrencyListActivity.class);
                startActivity(intent);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectmeeting=meeting.getText().toString();
                String amnt=amount.getText().toString();
                String expnsetype=expensetype.getText().toString();
                String dt=date.getText().toString();
                String loc=location.getText().toString();
                String vndr=vender.getText().toString();
                String cmnt=comments.getText().toString();
                String createddate=getDate.getText().toString();
                if(selectmeeting.equals("")){
                    Toast.makeText(NewExpenseActivity.this,"Select Meeting",Toast.LENGTH_SHORT).show();
                }
                else if(amnt.equals("")){
                    Toast.makeText(NewExpenseActivity.this,"Enter Amount",Toast.LENGTH_SHORT).show();
                }
                else if(expnsetype.equals("")){
                    Toast.makeText(NewExpenseActivity.this,"Enter ExpenseType",Toast.LENGTH_SHORT).show();
                }
                else if(dt.equals("")){
                    Toast.makeText(NewExpenseActivity.this,"Enter date",Toast.LENGTH_SHORT).show();
                }
                else if(loc.equals("")){
                    Toast.makeText(NewExpenseActivity.this,"Enter location",Toast.LENGTH_SHORT).show();
                }
                else if(vndr.equals("")){
                    Toast.makeText(NewExpenseActivity.this,"Enter Vender Name",Toast.LENGTH_SHORT).show();
                }
                else if(cmnt.equals("")){
                    Toast.makeText(NewExpenseActivity.this,"Enter Your Comment",Toast.LENGTH_SHORT).show();
                }
                else{

                    Toast.makeText(NewExpenseActivity.this,"saved",Toast.LENGTH_SHORT).show();
                }
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
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
    private void expensetypePopup() {
        final AlertDialog alertDialog;
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        // ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.activity_expensetype, null);
        String type[] = {"Entertainment", "Airfair", "Fuel", "hotel", "car rental", "Visa","Transport"};
        final ListView listexpsetype = dialogView.findViewById(R.id.listexpensetype);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, type);
        listexpsetype.setAdapter(adapter);
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        alertDialog.show();
        listexpsetype.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                // Display the selected item text on Edittext
                expensetype.setText(selectedItem);
                alertDialog.dismiss();
            }
        });
    }
}
