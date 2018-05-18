package com.example.lenovo.trackapp.activity;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.trackapp.R;

public class NewCustomerActivity extends AppCompatActivity {

    EditText customername,address,place,city,email,phone;
    Button cancel;
    TextView save;
    private TextInputLayout inputLayoutname,inputLayoutaddress,inputLayoutemail,inputLayoutphone,inputLayoutplace,inputLayoutcity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_customer);
        customername=(EditText)findViewById(R.id.edt_name);
        address=(EditText)findViewById(R.id.edt_address);
        email=(EditText)findViewById(R.id.edt_email);
        phone=(EditText)findViewById(R.id.edt_phone);
        place=(EditText)findViewById(R.id.edt_place);
        save=(TextView) findViewById(R.id.btn_save);
        city=(EditText) findViewById(R.id.edt_city);
        cancel=(Button)findViewById(R.id.btn_back);
        inputLayoutname = (TextInputLayout) findViewById(R.id.input_layout_customer);
        inputLayoutaddress=(TextInputLayout) findViewById(R.id.input_layout_address);
        inputLayoutemail=(TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutcity=(TextInputLayout) findViewById(R.id.input_layout_city);
        inputLayoutphone=(TextInputLayout) findViewById(R.id.input_layout_phone);
        inputLayoutplace=(TextInputLayout) findViewById(R.id.input_layout_place);
       /* customername.setTextColor(Color.parseColor("#2196F3"));
        address.setTextColor(Color.parseColor("#2196F3"));
        email.setTextColor(Color.parseColor("#2196F3"));
        phone.setTextColor(Color.parseColor("#2196F3"));
        place.setTextColor(Color.parseColor("#2196F3"));
        city.setTextColor(Color.parseColor("#2196F3"));*/
        customername.addTextChangedListener(new MyTextWatcher(customername));
        address.addTextChangedListener(new MyTextWatcher(address));
        email.addTextChangedListener(new MyTextWatcher(email));
        phone.addTextChangedListener(new MyTextWatcher(phone));
        place.addTextChangedListener(new MyTextWatcher(place));
        city.addTextChangedListener(new MyTextWatcher(city));
        getSupportActionBar().setTitle("New Customer");
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(NewCustomerActivity.this,AddCustomerActivity.class);
                startActivity(intent);
            }
        });
        save.setOnClickListener(new View.OnClickListener()
        {


            @Override
            public void onClick(View arg0) {
                submitForm();
            }
        });
    } private void submitForm() {
        if (!validateName()) {
            return;
        }
        if (!validateAddress()) {
            return;
        }
        if (!validateEmail()) {
            return;
        }
        if (!validatePlace()) {
            return;
        }
        if (!validatePhone()) {
            return;
        }
        if (!validateCity()) {
            return;
        }
        Toast.makeText(getApplicationContext(), "Thank You!", Toast.LENGTH_SHORT).show();
        finish();
        startActivity(getIntent());
    }
    private boolean validateName() {
        if (customername.getText().toString().trim().isEmpty()) {
            inputLayoutname.setError(getString(R.string.err_msg_name));
            requestFocus(customername);
            return false;
        } else {
            inputLayoutname.setErrorEnabled(false);
        }
        return true;
    }
    private boolean validateAddress() {
        if (address.getText().toString().trim().isEmpty()) {
            inputLayoutaddress.setError(getString(R.string.err_msg_address));
            requestFocus(address);
            return false;
        } else {
            inputLayoutaddress.setErrorEnabled(false);
        }
        return true;
    }
    private boolean validateEmail() {
        if (email.getText().toString().trim().isEmpty()||!email.getText().toString().matches("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+")) {
            inputLayoutemail.setError(getString(R.string.err_msg_email));
            requestFocus(email);
            return false;
        } else {
            inputLayoutemail.setErrorEnabled(false);
        }
        return true;
    }
    private boolean validatePhone() {
        if (phone.getText().toString().trim().isEmpty()||phone.getText().toString().length()<10||phone.getText().toString().length()>12) {
            inputLayoutphone.setError(getString(R.string.err_msg_phone));
            requestFocus(phone);
            return false;
        } else {
            inputLayoutphone.setErrorEnabled(false);
        }
        return true;
    }
    private boolean validatePlace() {
        if (place.getText().toString().trim().isEmpty()) {
            inputLayoutplace.setError(getString(R.string.err_msg_place));
            requestFocus(place);
            return false;
        } else {
            inputLayoutplace.setErrorEnabled(false);
        }
        return true;
    }
    private boolean validateCity() {
        if (city.getText().toString().trim().isEmpty()) {
            inputLayoutcity.setError(getString(R.string.err_msg_city));
            requestFocus(city);
            return false;
        } else {
            inputLayoutcity.setErrorEnabled(false);
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
                case R.id.edt_name:
                    validateName();
                    break;
                case R.id.edt_address:
                    validateAddress();
                    break;
                case R.id.edt_email:
                    validateEmail();
                    break;
                case R.id.edt_place:
                    validatePlace();
                    break;
                case R.id.edt_phone:
                    validatePhone();
                    break;
                case R.id.edt_city:
                    validateCity();
                    break;
            }
        }
    }
}
