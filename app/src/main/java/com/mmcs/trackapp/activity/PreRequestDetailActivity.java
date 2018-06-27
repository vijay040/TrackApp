package com.mmcs.trackapp.activity;


import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
;

import com.mmcs.trackapp.R;

import com.mmcs.trackapp.model.PreRequestModel;

public class PreRequestDetailActivity extends AppCompatActivity {
PreRequestModel prerequestmodel;
    TextView txtdescreption,txtdate,txtadvance,txtstatus,txtaddress,txtcustomer;
ListView list_requesttype;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_request_detail);
        prerequestmodel= (PreRequestModel) getIntent().getSerializableExtra("PREREQUESTMODEL");
        txtdescreption=findViewById(R.id.txtdescreption);
        txtdate=findViewById(R.id.txtdate);
        txtadvance=findViewById(R.id.txtadvance);
        txtstatus=findViewById(R.id.txtstatus);
        txtaddress=findViewById(R.id.txtaddress);
        txtcustomer=findViewById(R.id.txtcustomer);
        list_requesttype=findViewById(R.id.list_requesttype);


        txtdescreption.setText("Descreption:"+prerequestmodel.getComment());
        txtdate.setText("Date:"+prerequestmodel.getDate());
        txtadvance.setText("Advance:"+prerequestmodel.getAdvance());
        txtaddress.setText("Address:"+prerequestmodel.getAddress());
        txtcustomer.setText("Customer Name:"+prerequestmodel.getCustomer_name());
        txtstatus.setText("Status:"+prerequestmodel.getStatus());
        SpannableStringBuilder sb = new SpannableStringBuilder(txtdescreption.getText());
        // Span to set text color to some RGB value
        ForegroundColorSpan fcs = new ForegroundColorSpan(Color.parseColor("#5fb0c9"));
        // Span to make text bold
        //    final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD);
        // Set the text color for first 4 characters
        sb.setSpan(fcs, 0, 12, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        txtdescreption.setText(sb);

        sb = new SpannableStringBuilder(txtadvance.getText());
        fcs = new ForegroundColorSpan(Color.parseColor("#5fb0c9"));
        sb.setSpan(fcs, 0, 8, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        txtadvance.setText(sb);

        sb = new SpannableStringBuilder(txtdate.getText());
        fcs = new ForegroundColorSpan(Color.parseColor("#5fb0c9"));
        sb.setSpan(fcs, 0, 5, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        txtdate.setText(sb);

        sb = new SpannableStringBuilder(txtstatus.getText());
        fcs = new ForegroundColorSpan(Color.parseColor("#5fb0c9"));
        sb.setSpan(fcs, 0, 7, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        txtstatus.setText(sb);

        sb = new SpannableStringBuilder(txtaddress.getText());
        fcs = new ForegroundColorSpan(Color.parseColor("#5fb0c9"));
        sb.setSpan(fcs, 0, 8, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        txtaddress.setText(sb);

        sb = new SpannableStringBuilder(txtcustomer.getText());
        fcs = new ForegroundColorSpan(Color.parseColor("#5fb0c9"));
        sb.setSpan(fcs, 0, 13, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        txtcustomer.setText(sb);
        back();
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
