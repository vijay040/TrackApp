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
import java.util.Map;

public class ReminderdetailActivity extends AppCompatActivity {
    TextView txtdescreption, txtpurpose, txtcustomer, txtcontactperson, txtagenda, txtdate, txttime, txtaddress;
    Button ok;
    ImageView imageView;
    static Ringtone r;
    Shprefrences sh;
    Map<String, String> params;

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
       /* params = (Map<String, String>) getIntent().getExtras().get("NOTIFICATION_VALUE");


        txtdescreption.setText(params.get("description"));
        txtpurpose.setText(params.get("purpose"));
        txtcontactperson.setText(params.get("contact_person"));
        txtagenda.setText(params.get("agenda"));
        txtdate.setText(params.get("date"));
        txttime.setText(params.get("time"));
        txtaddress.setText(params.get("address"));
        txtcustomer.setText(params.get("title"));*/

        if (r == null) {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            r = RingtoneManager.getRingtone(this, notification);
        } else
            r.stop();
        r.play();


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                r.stop();
                finish();
            }
        });


    }

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(r.isPlaying())
        r.stop();
    }
}
