package com.mmcs.trackapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mmcs.trackapp.R;

public class ReminderdetailActivity extends AppCompatActivity {
TextView txtdescreption,txtpurpose,txtcustomer,txtcontactperson,txtagenda,txtdate,txttime,txtaddress;
Button ok;
ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminderdetail);
        txtdescreption=findViewById(R.id.txtdescreption);
        txtpurpose=findViewById(R.id.txtpurpose);
        txtcustomer=findViewById(R.id.txtcustomer);
        txtcontactperson=findViewById(R.id.txtcontactperson);
        txtagenda=findViewById(R.id.txtagenda);
        txtdate=findViewById(R.id.txtdate);
        txttime=findViewById(R.id.txttime);
        txtaddress=findViewById(R.id.txtaddress);
        ok=findViewById(R.id.btn);
        imageView=findViewById(R.id.image);
        Animation shake = AnimationUtils.loadAnimation(ReminderdetailActivity.this, R.anim.shake);
        imageView.startAnimation(shake);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });




    }
}
