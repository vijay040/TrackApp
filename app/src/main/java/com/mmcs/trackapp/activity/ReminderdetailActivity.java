package com.mmcs.trackapp.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mmcs.trackapp.R;
import com.mmcs.trackapp.model.AlarmModel;
import com.mmcs.trackapp.util.AlarmReciver;
import com.mmcs.trackapp.util.Shprefrences;

import java.util.Calendar;

public class ReminderdetailActivity extends AppCompatActivity {
    TextView txtdescreption, txtpurpose, txtcustomer, txtcontactperson, txtagenda, txtdate, txttime, txtaddress;
    Button ok;
    ImageView imageView;
    AlarmModel alarm;
    PendingIntent alarmIntent;
    int d, y, m, h, t;
    String remindertime[] = {"5 minutes", "10 minutes", "15 minutes", "20 minutes", "30 minutes", "1 hour", "2 hours", "3 hours", "4 hours", "5 hours",
            "6 hours", "7 hours", "8 hours", " 9 hours", "10 hours", "11 hours", "18 hours", "1 day", "2 days", "3 days", "4 days", "1 week", "2 weeks"};
   static Ringtone r;
   Shprefrences sh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminderdetail);
        txtdescreption = findViewById(R.id.txtdescreption);
        txtpurpose = findViewById(R.id.txtpurpose);
        txtcustomer = findViewById(R.id.txtcustomer);
        txtcontactperson = findViewById(R.id.txtcontactperson);
        txtagenda = findViewById(R.id.txtagenda);
        txtdate = findViewById(R.id.txtdate);
        txttime = findViewById(R.id.txttime);
        txtaddress = findViewById(R.id.txtaddress);
        ok = findViewById(R.id.btnOK);
        imageView = findViewById(R.id.image);
        Animation shake = AnimationUtils.loadAnimation(ReminderdetailActivity.this, R.anim.shake);
        imageView.startAnimation(shake);
        sh = new Shprefrences(this);

       /* String strtdt = edtStartDate.getText() + "";
        String strttm = edt_time.getText() + "";
        String enddt = edtEndDate.getText() + "";
        String endtm = edt_EndTime.getText() + "";
        String endtimer = edt_EndTimer.getSelectedItem() + "";
        alarm.setAlarmTime(endtimer);
        alarm.setEndDate(enddt);
        alarm.setEndTime(endtm);
        alarm.setStartDate(strtdt);
        alarm.setStartTime(strttm);*/
        Intent intent = new Intent();
        intent.putExtra("ALARM", alarm);
        setResult(RESULT_OK, intent);
      // r=sh.getRington("RINGTON");
       if(r==null) {
           Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
           r = RingtoneManager.getRingtone(this, notification);
          // sh.setRington("RINGTON",r);
       }else
       r.stop();
       r.play();
               /* Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(System.currentTimeMillis());
                cal.clear();
                cal.set(2018,5,30,22,28);*/

        Calendar cal = Calendar.getInstance();

        cal.setTimeInMillis(System.currentTimeMillis());
        cal.clear();
        cal.set(y, m, d, h, t);

       /* final AlarmManager alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent inte = new Intent(ReminderdetailActivity.this, AlarmReciver.class);
        alarmIntent = PendingIntent.getBroadcast(ReminderdetailActivity.this, 302, inte, PendingIntent.FLAG_UPDATE_CURRENT);
        cal.add(Calendar.SECOND, 5);
        alarmMgr.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), alarmIntent);*/

        //alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 900, alarmIntent);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                r.stop();
              /*  alarmIntent.cancel();
                alarmMgr.cancel(alarmIntent);
                Toast.makeText(ReminderdetailActivity.this, "Alarm Stopped", Toast.LENGTH_SHORT).show();*/
                finish();
            }
        });


    }
}
