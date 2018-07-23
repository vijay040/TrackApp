package com.mmcs.trackapp.activity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.mmcs.trackapp.R;
import com.mmcs.trackapp.model.AlarmModel;
import com.mmcs.trackapp.util.AlarmReciver;

import java.util.Calendar;

public class ReminderActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText edtStartDate, edt_time, edtEndDate, edt_EndTime;
    Button btnOk, btnfollowup;
    Spinner edt_EndTimer;
    int H, M;
    Calendar calendar;
    int DD, MM, YY;
    static final int DATE_DIALOG_ID = 1;
    static final int DATE_DIALOG_ID2 = 2;
    static final int TIME_DIALOG_ID = 3;
    static final int TIME_DIALOG_ID2 = 4;
    int cur = 0;
    AlarmModel alarm;
    Calendar alarmCal;
    int d, y, m, h, t;
    String remindertime[] = {"5 minutes", "10 minutes", "15 minutes", "20 minutes", "30 minutes", "1 hour", "2 hours", "3 hours", "4 hours", "5 hours",
            "6 hours", "7 hours", "8 hours", " 9 hours", "10 hours", "11 hours", "18 hours", "1 day", "2 days", "3 days", "4 days", "1 week", "2 weeks"};
    public static String startDate, startTime, endDate, endTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminder_meetings_popup);
        edtStartDate = findViewById(R.id.edtStartDate);
        edt_time = findViewById(R.id.edt_time);
        edtEndDate = findViewById(R.id.edtEndDate);
        edt_EndTime = findViewById(R.id.edt_EndTime);
        edt_EndTimer = findViewById(R.id.edt_EndTimer);
        btnOk = findViewById(R.id.btnOk);
        btnfollowup = findViewById(R.id.btnfollow);
        calendar = Calendar.getInstance();
        setTitle();
        back();
        alarm = new AlarmModel();
        DD = calendar.get(Calendar.DAY_OF_MONTH);
        MM = calendar.get(Calendar.MONTH);
        YY = calendar.get(Calendar.YEAR);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, remindertime);
        edt_EndTimer.setAdapter(adapter);
        edtStartDate.setText(startDate);
        edt_time.setText(startTime);

        btnfollowup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReminderActivity.this, ReminderdetailActivity.class));
            }
        });

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
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strtdt = edtStartDate.getText() + "";
                String strttm = edt_time.getText() + "";
                String enddt = edtEndDate.getText() + "";
                String endtm = edt_EndTime.getText() + "";
                String endtimer = edt_EndTimer.getSelectedItem() + "";
                alarm.setAlarmTime(endtimer);
                alarm.setEndDate(enddt);
                alarm.setEndTime(endtm);
                alarm.setStartDate(strtdt);
                alarm.setStartTime(strttm);
                Intent intent = new Intent();
                intent.putExtra("ALARM", alarm);
                setResult(RESULT_OK, intent);
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
            case TIME_DIALOG_ID:
                System.out.println("onCreateDialog  : " + id);
                cur = TIME_DIALOG_ID;
                return new TimePickerDialog(this, onTimeSetListener, H, M, true);
            case TIME_DIALOG_ID2:
                System.out.println("onCreateDialog  : " + id);
                cur = TIME_DIALOG_ID2;
                return new TimePickerDialog(this, onTimeSetListener, H, M, true);

        }

        return null;
    }

    DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int d, int m, int y) {
            if (cur == DATE_DIALOG_ID) {
                // set selected date into textview
                if ((m + 1) < 10)
                    edtStartDate.setText(String.valueOf(d) + "-0" + String.valueOf(m + 1) + "-" + String.valueOf(y));
                else
                    edtStartDate.setText(String.valueOf(d) + "-" + String.valueOf(m + 1) + "-" + String.valueOf(y));
                ReminderActivity.this.d = d;
                ReminderActivity.this.m = m;
                ReminderActivity.this.y = y;
            } else {
                if ((m + 1) < 10)
                    edtEndDate.setText(String.valueOf(d) + "-0" + String.valueOf(m + 1) + "-" + String.valueOf(y));
                else
                    edtEndDate.setText(String.valueOf(d) + "-" + String.valueOf(m + 1) + "-" + String.valueOf(y));
            }
        }
    };
    TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int h, int m) {
            timePicker.is24HourView();
            if (cur == TIME_DIALOG_ID) {
                // set selected date into textview
               /* if (h < 12 && h >= 0) {
                    edt_time.setText(String.valueOf(h) + ":" + String.valueOf(m) + " " + "AM");
                } else {
                    h -= 12;
                    if (h == 0) {
                        h = 12;
                    }
                    edt_time.setText(String.valueOf(h) + ":" + String.valueOf(m) + " " + "PM");
                }*/
                edt_time.setText(String.valueOf(h) + ":" + String.valueOf(m));
                ReminderActivity.this.h = h;
                ReminderActivity.this.t = t;

            } else {
               /*
                if (h < 12 && h >= 0) {
                    edt_EndTime.setText(String.valueOf(h) + ":" + String.valueOf(m) + " " + "AM");
                } else {
                    h -= 12;
                    if (h == 0) {
                        h = 12;
                    }
                    edt_EndTime.setText(String.valueOf(h) + ":" + String.valueOf(m) + " " + "PM");
                }*/
                edt_EndTime.setText(String.valueOf(h) + ":" + String.valueOf(m));
            }
        }
    };

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        int s_id = edt_EndTimer.getSelectedItemPosition();
        Toast.makeText(this, "Selected Reminder:" + remindertime[s_id], Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void setAlarm() {

    }

    private void setTitle() {
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(getString(R.string.reminder_settings));
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

}
