package com.example.lenovo.trackapp.activity;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lenovo.trackapp.R;
import com.example.lenovo.trackapp.model.PreRequestModel;

;

public class PendingDetailActivity extends AppCompatActivity {
PreRequestModel prerequestmodel;
TextView txtdescreption,txtdate,txtadvance,txtstatus;
ListView list_requesttype;
Button reject,approve;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pendingdetail);
        prerequestmodel= (PreRequestModel) getIntent().getSerializableExtra("PREREQUESTMODEL");
        txtdescreption=findViewById(R.id.txtdescreption);
        txtdate=findViewById(R.id.txtdate);
        txtadvance=findViewById(R.id.txtadvance);

        reject=findViewById(R.id.reject);
        approve=findViewById(R.id.approve);
        getSupportActionBar().setTitle("Request");
        list_requesttype=findViewById(R.id.list_requesttype);
        txtdescreption.setText("Descreption:"+prerequestmodel.getComment());
        txtdate.setText("Date:"+prerequestmodel.getDate());
        txtadvance.setText("Advance:"+prerequestmodel.getAdvance());

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


    }
}
