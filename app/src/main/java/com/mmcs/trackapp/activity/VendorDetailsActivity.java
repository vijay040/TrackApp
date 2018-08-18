package com.mmcs.trackapp.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mmcs.trackapp.R;

import java.util.Calendar;

public class VendorDetailsActivity extends AppCompatActivity {
    EditText edtQuotation, edtport_destination, edtVersion, edtsubject, edtStaffName, edtReceiveDate, edtValidDate, edtport_loading;
    static final int DATE_DIALOG_ID = 1;
    static final int DATE_DIALOG_ID2 = 2;
    int DD, MM, YY;
    int cur = 0;
    Calendar calendar;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_details);
        edtQuotation = findViewById(R.id.edtQuotation);
        edtport_loading = findViewById(R.id.edtport_loading);
        edtport_destination = findViewById(R.id.edtport_destination);
        edtVersion = findViewById(R.id.edtVersion);
        edtsubject = findViewById(R.id.edtsubject);
        edtStaffName = findViewById(R.id.edtStaffName);
        edtReceiveDate = findViewById(R.id.edtReceiveDate);
        edtValidDate = findViewById(R.id.edtValidDate);
        btnSubmit = findViewById(R.id.btnSubmit);
        calendar = Calendar.getInstance();
        DD = calendar.get(Calendar.DAY_OF_MONTH);
        MM = calendar.get(Calendar.MONTH);
        YY = calendar.get(Calendar.YEAR);
        edtQuotation.setText("QUOTATION NO:VXLKD124");
        edtVersion.setText("VESION NO:version 2.0");
        edtsubject.setText("SUBJECT:Vendor Quotation Details");
        edtStaffName.setText("STAFF NAME:Mr.Vijay");
        edtValidDate.setText("VALID TILL:12/12/2018");
        edtport_loading.setText("PORT OF LOADING:MUMBAI");
        edtport_destination.setText("PORT OF DESTINATION:INDONESIA");

        if ((MM + 1) < 10)
            edtReceiveDate.setText(String.valueOf(YY) + "-0" + String.valueOf(MM + 1) + "-" + String.valueOf(DD));
        else
            edtReceiveDate.setText(String.valueOf(YY) + "-" + String.valueOf(MM + 1) + "-" + String.valueOf(DD));
        edtReceiveDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DATE_DIALOG_ID);
            }
        });
        edtValidDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DATE_DIALOG_ID2);
            }
        });
        setTitle();
        back();
    }
    private void setTitle() {
        TextView title = (TextView) findViewById(R.id.title);
        title.setText("VENDOR QUOTATION");
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
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                System.out.println("onCreateDialog  : " + id);
                cur = DATE_DIALOG_ID;
                return new DatePickerDialog(this, onDateSetListener, YY, MM, DD);
            case DATE_DIALOG_ID2:
                cur = DATE_DIALOG_ID2;
                System.out.println("onCreateDialog2  : " + id);
                return new DatePickerDialog(this, onDateSetListener, YY, MM, DD);
        }
        return null;
    }
    DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int d, int m, int y) {
            if (cur == DATE_DIALOG_ID) {
                // set selected date into textview
                if ((m + 1) < 10)
                    edtReceiveDate.setText(String.valueOf(d) + "-0" + String.valueOf(m + 1) + "-" + String.valueOf(y));
                else
                    edtReceiveDate.setText(String.valueOf(d) + "-" + String.valueOf(m + 1) + "-" + String.valueOf(y));
            }
            else    {
                if ((m + 1) < 10)
                    edtValidDate.setText(String.valueOf(d) + "-0" + String.valueOf(m + 1) + "-" + String.valueOf(y));
                else
                    edtValidDate.setText(String.valueOf(d) + "-" + String.valueOf(m + 1) + "-" + String.valueOf(y));
            }
        }
    };
}

