package com.mmcs.trackapp.activity;
import android.content.Intent;
import android.net.MailTo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.mmcs.trackapp.R;

public class SettingActivity extends AppCompatActivity {
EditText edt_txt_currency;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getSupportActionBar().setTitle("Settings");
        edt_txt_currency=findViewById(R.id.edt_txt_currency);

    }
}
