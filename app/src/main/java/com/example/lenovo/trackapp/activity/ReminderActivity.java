package com.example.lenovo.trackapp.activity;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.lenovo.trackapp.R;
import com.example.lenovo.trackapp.model.AlarmModel;
import com.example.lenovo.trackapp.util.AlarmReciver;

import java.util.Calendar;

public class ReminderActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText edtStartDate, edt_time, edtEndDate, edt_EndTime;
    Button btnOk;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminder_meetings_popup);
        edtStartDate = findViewById(R.id.edtStartDate);
        edt_time = findViewById(R.id.edt_time);
        edtEndDate = findViewById(R.id.edtEndDate);
        edt_EndTime = findViewById(R.id.edt_EndTime);
        edt_EndTimer = findViewById(R.id.edt_EndTimer);
        btnOk = findViewById(R.id.btnOk);
        calendar = Calendar.getInstance();
        alarm = new AlarmModel();
        DD = calendar.get(Calendar.DAY_OF_MONTH);
        MM = calendar.get(Calendar.MONTH);
        YY = calendar.get(Calendar.YEAR);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, remindertime);
        edt_EndTimer.setAdapter(adapter);
        edtStartDate.setText(String.valueOf(DD) + "-" + String.valueOf(MM + 1) + "-" + String.valueOf(YY));
        edtEndDate.setText(String.valueOf(DD) + "-" + String.valueOf(MM + 1) + "-" + String.valueOf(YY));
        Calendar alarmCal = Calendar.getInstance();
        H = calendar.get(Calendar.HOUR_OF_DAY);
        M = calendar.get(Calendar.MINUTE);
        if (H < 12 && H >= 0) {
            edt_time.setText(String.valueOf(H) + ":" + String.valueOf(M) + " " + "AM");
            edt_EndTime.setText(String.valueOf(H) + ":" + String.valueOf(M) + " " + "AM");
        } else {
            H -= 12;
            if (H == 0) {
                H = 12;
            }
            edt_time.setText(String.valueOf(H) + ":" + String.valueOf(M) + " " + "PM");
            edt_EndTime.setText(String.valueOf(H) + ":" + String.valueOf(M) + " " + "AM");
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

               /* Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(System.currentTimeMillis());
                cal.clear();
                cal.set(2018,5,30,22,28);*/

                Calendar cal = Calendar.getInstance();

                cal.setTimeInMillis(System.currentTimeMillis());
                cal.clear();
                cal.set(y, m, d, h, t);

                AlarmManager alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Intent inte = new Intent(ReminderActivity.this, AlarmReciver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(ReminderActivity.this, 0, inte, 0);
                // cal.add(Calendar.SECOND, 5);
                alarmMgr.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);

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
            if (cur == DATE_DIALOG_ID) {
                // set selected date into textview
                edtStartDate.setText(String.valueOf(d) + "-" + String.valueOf(m + 1) + "-" + String.valueOf(y));
                ReminderActivity.this.d = d;
                ReminderActivity.this.m = m;
                ReminderActivity.this.y = y;
            } else {

                edtEndDate.setText(String.valueOf(d) + "-" + String.valueOf(m + 1) + "-" + String.valueOf(y));
            }
        }
    };
    TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int h, int m) {
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        int s_id = edt_EndTimer.getSelectedItemPosition();
        Toast.makeText(this, "Selected Reminder:" + remindertime[s_id], Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
