package com.example.lenovo.trackapp.actv;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.widget.TextView;

import com.example.lenovo.trackapp.R;
import com.example.lenovo.trackapp.model.MeetingModel;

public class MeetingDetailsActivity extends AppCompatActivity {
TextView descreption,purpose,customer,agenda,date,time,address,contact;
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
        contact=findViewById(R.id.txtcontactperson);
        descreption.setText("Description: "+model.getDescreption());
        purpose.setText("Purpose: "+model.getPurpose());
        customer.setText("Client  :"+model.getCustomer_name());
        agenda.setText("Agenda:"+model.getAgenda());
        date.setText("Date: "+model.getDate());
        time.setText("Time: "+model.getTime());
        address.setText("Address: "+model.getAddress());
        contact.setText("Contact Person: "+model.getContact_person());
        SpannableStringBuilder sb = new SpannableStringBuilder(purpose.getText());
     // Span to set text color to some RGB value
         ForegroundColorSpan fcs = new ForegroundColorSpan(Color.parseColor("#5fb0c9"));
        // Span to make text bold
    //    final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD);
        // Set the text color for first 4 characters
        sb.setSpan(fcs, 0, 8, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

// make them also bold
       // sb.setSpan(bss, 0, 8, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        purpose.setText(sb);
        sb = new SpannableStringBuilder(descreption.getText());
        fcs = new ForegroundColorSpan(Color.parseColor("#5fb0c9"));
        sb.setSpan(fcs, 0, 12, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        descreption.setText(sb);

        sb = new SpannableStringBuilder(customer.getText());
        fcs = new ForegroundColorSpan(Color.parseColor("#5fb0c9"));
        sb.setSpan(fcs, 0, 9, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        customer.setText(sb);

        sb = new SpannableStringBuilder(agenda.getText());
        fcs = new ForegroundColorSpan(Color.parseColor("#5fb0c9"));
        sb.setSpan(fcs, 0, 7, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        agenda.setText(sb);

        sb = new SpannableStringBuilder(date.getText());
        fcs = new ForegroundColorSpan(Color.parseColor("#5fb0c9"));
        sb.setSpan(fcs, 0, 5, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        date.setText(sb);

        sb = new SpannableStringBuilder(time.getText());
        fcs = new ForegroundColorSpan(Color.parseColor("#5fb0c9"));
        sb.setSpan(fcs, 0, 5, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        time.setText(sb);

        sb = new SpannableStringBuilder(address.getText());
        fcs = new ForegroundColorSpan(Color.parseColor("#5fb0c9"));
        sb.setSpan(fcs, 0, 8, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        address.setText(sb);

        sb = new SpannableStringBuilder(contact.getText());
        fcs = new ForegroundColorSpan(Color.parseColor("#5fb0c9"));
        sb.setSpan(fcs, 0, 15, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        contact.setText(sb);
        }
}
