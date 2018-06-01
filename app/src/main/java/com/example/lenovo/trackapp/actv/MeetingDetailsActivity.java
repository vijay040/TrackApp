package com.example.lenovo.trackapp.actv;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.lenovo.trackapp.R;
import com.example.lenovo.trackapp.model.MeetingModel;

public class MeetingDetailsActivity extends AppCompatActivity {
TextView descreption,purpose,customer,agenda,date,time,address;
    MeetingModel model;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_meetingdetails);
        model= (MeetingModel) getIntent().getSerializableExtra("MEETINGMODEL");
        descreption=findViewById(R.id.txtdescreption);
        purpose=findViewById(R.id.txtpurpose);
        customer=findViewById(R.id.txtcustomer);
        agenda=findViewById(R.id.txtagenda);
        date=findViewById(R.id.txtdate);
        time=findViewById(R.id.txttime);
        address=findViewById(R.id.txtaddress);
         descreption.setText("Descreption: "+model.getDescreption());
        purpose.setText("Purpose: "+model.getPurpose());
        customer.setText("Customer Name: "+model.getCustomer());
        agenda.setText("Agenda: "+model.getAgenda());
        date.setText("Date: "+model.getDate());
        time.setText("Time: "+model.getTime());
        address.setText("Address: "+model.getAddress());


    }
}
