package com.example.lenovo.trackapp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.trackapp.db.CreateMeetDB;
import com.example.lenovo.trackapp.R;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NewExpenseActivity extends AppCompatActivity {

    private static final int SELECT_PHOTO = 200;
    EditText date, location, vender, expensetype, getDate, amount, comments;
    private static final int CAMERA_REQUEST = 1888;
    Bitmap bmp;
    ImageView imageView;
    TextView textView;
    static String value, cate;
    // String amnt;
    public static String imgUrl;

    private TextInputLayout inputLayoutamount, inputLayoutdate, inputLayoutlocation, inputLayoutvender, inputLayoutcomment, inputLayoutexpensetype;
    private static final String IMAGE_DIRECTORY = "/demonuts";
    CreateMeetDB database = new CreateMeetDB(this);
    private String TAG;
    public static final String MY_PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_expense);
        getSupportActionBar().setTitle("ExpenseActivity");
        amount = (EditText) findViewById(R.id.edt_amount);
        //Intent intent1 = getIntent();
        //  amnt=intent1.getStringExtra("amount");
        // amount.setText(amnt);
        date = (EditText) findViewById(R.id.edt_date);
        location = (EditText) findViewById(R.id.edt_location);
        imageView = (ImageView) findViewById(R.id.imz_loadreceipt);
        vender = (EditText) findViewById(R.id.edt_vendor);
        textView = (TextView) findViewById(R.id.btn_load);
        comments = (EditText) findViewById(R.id.edt_comment);
        expensetype = (EditText) findViewById(R.id.edt_Expensetype);
        getDate = (EditText) findViewById(R.id.edt_date1);
        inputLayoutamount = (TextInputLayout) findViewById(R.id.input_layout_amount);
        inputLayoutexpensetype = (TextInputLayout) findViewById(R.id.input_layout_expensetype);
        inputLayoutdate = (TextInputLayout) findViewById(R.id.input_layout_date);
        inputLayoutlocation = (TextInputLayout) findViewById(R.id.input_layout_location);
        inputLayoutvender = (TextInputLayout) findViewById(R.id.input_layout_vendor);
        inputLayoutcomment = (TextInputLayout) findViewById(R.id.input_layout_comment);
        amount.addTextChangedListener(new MyTextWatcher(amount));
        date.addTextChangedListener(new MyTextWatcher(date));
        location.addTextChangedListener(new MyTextWatcher(location));
        vender.addTextChangedListener(new MyTextWatcher(vender));
        expensetype.addTextChangedListener(new MyTextWatcher(expensetype));
        comments.addTextChangedListener(new MyTextWatcher(comments));

        if (imgUrl != null && !imgUrl.equalsIgnoreCase(""))
            Picasso.get().load(imgUrl).into(imageView);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getString("amount");
        }
        amount.setText(value);
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        cate = prefs.getString("categoryitem", "");//"No name defined" is the default value.
        expensetype.setText(cate);
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
        expensetype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewExpenseActivity.this, AddCategoryActivity.class);
                startActivity(intent);
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
        Cursor cursor = database.fetchData();
        //  getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("536DFE")));
        if (!(cursor.moveToFirst()) || cursor.getCount() == 0) {
            Toast.makeText(NewExpenseActivity.this, "there is no schedule to show", Toast.LENGTH_LONG).show();
        } else {
            String P = cursor.getString(cursor.getColumnIndex("place"));
            String Dt = cursor.getString(cursor.getColumnIndex("date"));
            String CN = cursor.getString(cursor.getColumnIndex("contactperson"));
            date.setText(Dt);
            location.setText(P);
            vender.setText(CN);
        }
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.expenseactn, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        int itemid = item.getItemId();
        if (itemid == R.id.save) {
            submitForm();
        } else if (itemid == R.id.cancel) {
            Intent intent = new Intent(NewExpenseActivity.this, ExpenseActivity.class);
            startActivity(intent);
            Toast.makeText(NewExpenseActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void submitForm() {
        if (!validateAmount()) {
            return;
        }
        if (!validateDate()) {
            return;
        }
        if (!validateLocation()) {
            return;
        }
        if (!validateVender()) {
            return;
        }
        if (!validateExpenseType()) {
            return;
        }
        if (!validateComment()) {
            return;
        }
        Toast.makeText(getApplicationContext(), "Thank You!", Toast.LENGTH_SHORT).show();
        finish();
        startActivity(getIntent());
    }

    private boolean validateAmount() {
        if (amount.getText().toString().trim().isEmpty()) {
            inputLayoutamount.setError("Enter Amount");
            requestFocus(amount);
            return false;
        } else {
            inputLayoutamount.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateDate() {
        if (date.getText().toString().trim().isEmpty()) {
            inputLayoutdate.setError("Enter Date");
            requestFocus(date);
            return false;
        } else {
            inputLayoutdate.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateLocation() {
        if (location.getText().toString().trim().isEmpty()) {
            inputLayoutlocation.setError("Enter Location");
            requestFocus(location);
            return false;
        } else {
            inputLayoutlocation.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateVender() {
        if (vender.getText().toString().trim().isEmpty()) {
            inputLayoutvender.setError("Enter Vender Name");
            requestFocus(vender);
            return false;
        } else {
            inputLayoutvender.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateExpenseType() {
        if (expensetype.getText().toString().trim().isEmpty()) {
            inputLayoutexpensetype.setError("Choose ExpenseActivity type");
            requestFocus(vender);
            return false;
        } else {
            inputLayoutvender.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateComment() {
        if (comments.getText().toString().trim().isEmpty()) {
            inputLayoutcomment.setError("Enter Your Comment");
            requestFocus(comments);
            return false;
        } else {
            inputLayoutcomment.setErrorEnabled(false);
        }
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public class MyTextWatcher implements TextWatcher {
        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.edt_amount:
                    validateAmount();
                    break;
                case R.id.edt_date:
                    validateDate();
                    break;
                case R.id.edt_location:
                    validateLocation();
                    break;
                case R.id.edt_vendor:
                    validateVender();
                    break;
                case R.id.edt_Expensetype:
                    validateExpenseType();
                    break;
                case R.id.edt_comment:
                    validateComment();
                    break;
            }
        }
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

}
