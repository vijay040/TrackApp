package com.mmcs.trackapp.activity;


import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
;

import com.mmcs.trackapp.R;

import com.mmcs.trackapp.adaptor.RequestTypesStrAdaptor;
import com.mmcs.trackapp.model.PreRequestModel;

public class PreRequestDetailActivity extends AppCompatActivity {
PreRequestModel prerequestmodel;
    TextView txtdescreption,txtdate,txtadvance,txtstatus,txtaddress,txtcustomer;
ListView listTypes;
Button ok;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_request_detail);
        prerequestmodel= (PreRequestModel) getIntent().getSerializableExtra(getString(R.string.prerequest_model));
        txtdescreption=findViewById(R.id.txtdescreption);
        txtdate=findViewById(R.id.txtdate);
        txtadvance=findViewById(R.id.txtadvance);
        txtstatus=findViewById(R.id.txtstatus);
        txtaddress=findViewById(R.id.txtaddress);
        txtcustomer=findViewById(R.id.txtcustomer);
        listTypes=findViewById(R.id.listTypes);
        ok=findViewById(R.id.btn_ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        final RequestTypesStrAdaptor adaptor =new RequestTypesStrAdaptor(PreRequestDetailActivity.this,prerequestmodel.getRequest_type());
        listTypes.setAdapter(adaptor);
        listTypes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int
                    position, long id) {
                RequestTypesStrAdaptor adapter = (RequestTypesStrAdaptor) parent.getAdapter();
                String type = adapter.list.get(position);
                Intent intent = new Intent(PreRequestDetailActivity.this,PreRequestStatusActivity.class);
                intent.putExtra("reqst_type", type);
                intent.putExtra("id",prerequestmodel.getId());
                startActivity(intent);
            }
        });
        txtdescreption.setText(getString(R.string.description)+prerequestmodel.getComment());
        txtdate.setText(getString(R.string.date)+prerequestmodel.getDate());
        txtadvance.setText(getString(R.string.advance)+prerequestmodel.getAdvance());
        txtaddress.setText(getString(R.string.address)+prerequestmodel.getAddress());
        txtcustomer.setText(getString(R.string.customer_name)+prerequestmodel.getCustomer_name());
        txtstatus.setText(getString(R.string.status)+prerequestmodel.getStatus());
        SpannableStringBuilder sb = new SpannableStringBuilder(txtdescreption.getText());
        // Span to set text color to some RGB value
        ForegroundColorSpan fcs = new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary));
        // Span to make text bold
        //    final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD);
        // Set the text color for first 4 characters
        sb.setSpan(fcs, 0, 12, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        txtdescreption.setText(sb);

        sb = new SpannableStringBuilder(txtadvance.getText());
        fcs = new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary));
        sb.setSpan(fcs, 0, 8, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        txtadvance.setText(sb);

        sb = new SpannableStringBuilder(txtdate.getText());
        fcs = new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary));
        sb.setSpan(fcs, 0, 5, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        txtdate.setText(sb);

        sb = new SpannableStringBuilder(txtstatus.getText());
        fcs = new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary));
        sb.setSpan(fcs, 0, 7, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        txtstatus.setText(sb);

        sb = new SpannableStringBuilder(txtaddress.getText());
        fcs = new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary));
        sb.setSpan(fcs, 0, 8, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        txtaddress.setText(sb);

        sb = new SpannableStringBuilder(txtcustomer.getText());
        fcs = new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary));
        sb.setSpan(fcs, 0, 13, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        txtcustomer.setText(sb);
        back();
        setTitle();
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
    private void setTitle()
    {
        TextView title= (TextView) findViewById(R.id.title);
        title.setText(getString(R.string.pre_request_details));
    }

}
