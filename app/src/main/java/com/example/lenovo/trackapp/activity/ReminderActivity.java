package com.example.lenovo.trackapp.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.example.lenovo.trackapp.R;

import java.util.Calendar;

public class ReminderActivity extends AppCompatActivity {
    EditText edtStartDate,edt_time,edtEndDate,edt_EndTime,edt_EndTimer;
    int H, M;
    Calendar calendar;
    int DD, MM, YY;
    static final int DATE_DIALOG_ID = 1;
    static final int DATE_DIALOG_ID2 = 2;
    static final int TIME_DIALOG_ID = 3;
    static final int TIME_DIALOG_ID2 = 4;
    int cur = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminder_meetings_popup);
        edtStartDate=findViewById(R.id.edtStartDate);
        edt_time=findViewById(R.id.edt_time);
        edtEndDate=findViewById(R.id.edtEndDate);
        edt_EndTime=findViewById(R.id.edt_EndTime);
        edt_EndTimer=findViewById(R.id.edt_EndTimer);
        calendar=Calendar.getInstance();
        DD=calendar.get(Calendar.DAY_OF_MONTH);
        MM=calendar.get(Calendar.MONTH);
        YY=calendar.get(Calendar.YEAR);
        edtStartDate.setText(String.valueOf(DD)+"-"+String.valueOf(MM+1)+"-"+String.valueOf(YY));
        edtEndDate.setText(String.valueOf(DD)+"-"+String.valueOf(MM+1)+"-"+String.valueOf(YY));
        H = calendar.get(Calendar.HOUR_OF_DAY);
        M = calendar.get(Calendar.MINUTE);
        if (H < 12 && H >= 0) {
            edt_time.setText(String.valueOf(H)+":"+String.valueOf(M)+" "+"AM");
            edt_EndTime.setText(String.valueOf(H)+":"+String.valueOf(M)+" "+"AM");
        } else {
            H -= 12;
            if(H == 0) {
                H= 12;
            }
            edt_time.setText(String.valueOf(H)+":"+String.valueOf(M)+" "+"PM");
            edt_EndTime.setText(String.valueOf(H)+":"+String.valueOf(M)+" "+"AM");
        }

        edtStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DATE_DIALOG_ID);
            }
        });
        edtEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DATE_DIALOG_ID2);
            }
        });
        edt_time.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                showDialog(TIME_DIALOG_ID);
                }
        });
        edt_EndTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                showDialog(TIME_DIALOG_ID2);

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
            case TIME_DIALOG_ID:
                System.out.println("onCreateDialog  : " + id);
                cur = TIME_DIALOG_ID;
                return new TimePickerDialog(this, onTimeSetListener, H, M, false);
            case TIME_DIALOG_ID2:
                System.out.println("onCreateDialog  : " + id);
                cur = TIME_DIALOG_ID2;
                return new TimePickerDialog(this, onTimeSetListener, H, M, false);

        }

        return null;
    }
    DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int d, int m, int y) {
            if(cur == DATE_DIALOG_ID){
                // set selected date into textview
                edtStartDate.setText(String.valueOf(d) + "-" + String.valueOf(m + 1) + "-" + String.valueOf(y));
            }
else{
                edtEndDate.setText(String.valueOf(d) + "-" + String.valueOf(m + 1) + "-" + String.valueOf(y));
            }
            }
    };
    TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int h, int m) {
            if(cur == TIME_DIALOG_ID){
                // set selected date into textview
                if (h < 12 && h >= 0) {
                    edt_time.setText(String.valueOf(h) + ":" + String.valueOf(m) + " " + "AM");
                } else {
                    h -= 12;
                    if (h == 0) {
                        h = 12;
                    }
                    edt_time.setText(String.valueOf(h) + ":" + String.valueOf(m) + " " + "PM");
                }
                }
            else{
                if (h < 12 && h >= 0) {
                    edt_EndTime.setText(String.valueOf(h) + ":" + String.valueOf(m) + " " + "AM");
                } else {
                    h -= 12;
                    if (h == 0) {
                        h = 12;
                    }
                    edt_EndTime.setText(String.valueOf(h) + ":" + String.valueOf(m) + " " + "PM");
                }

            }
            }
    };
}
