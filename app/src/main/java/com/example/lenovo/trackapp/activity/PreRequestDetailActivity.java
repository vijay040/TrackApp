package com.example.lenovo.trackapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lenovo.trackapp.R;
import com.example.lenovo.trackapp.model.MeetingModel;
import com.example.lenovo.trackapp.model.PreRequestModel;

public class PreRequestDetailActivity extends AppCompatActivity {
PreRequestModel prerequestmodel;
TextView txtdescreption,txtdate,txtadvance,txtstatus;
ListView list_requesttype;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_request_detail);
        prerequestmodel= (PreRequestModel) getIntent().getSerializableExtra("PREREQUESTMODEL");
        txtdescreption=findViewById(R.id.txtdescreption);
        txtdate=findViewById(R.id.txtdate);
        txtadvance=findViewById(R.id.txtadvance);
        txtstatus=findViewById(R.id.txtstatus);
        list_requesttype=findViewById(R.id.list_requesttype);
        txtdescreption.setText("Descreption:"+prerequestmodel.getComment());
        txtdate.setText("Date:"+prerequestmodel.getDate());
        txtadvance.setText("Advance:"+prerequestmodel.getAdvance());

    }
}
