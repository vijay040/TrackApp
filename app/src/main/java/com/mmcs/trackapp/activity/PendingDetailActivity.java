package com.mmcs.trackapp.activity;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mmcs.trackapp.R;
import com.mmcs.trackapp.model.LoginModel;
import com.mmcs.trackapp.model.PreRequestModel;
import com.mmcs.trackapp.model.PreRequestResMeta;
import com.mmcs.trackapp.util.Shprefrences;
import com.mmcs.trackapp.util.Singleton;

;import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendingDetailActivity extends AppCompatActivity {
PreRequestModel prerequestmodel;
TextView txtdescreption,txtdate,txtadvance,txtstatus;
ListView list_requesttype;
Button reject,approve;
Shprefrences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        sh=new Shprefrences(this);
        setContentView(R.layout.activity_pendingdetail);
        prerequestmodel= (PreRequestModel) getIntent().getSerializableExtra("PREREQUESTMODEL");
        txtdescreption=findViewById(R.id.txtdescreption);
        txtdate=findViewById(R.id.txtdate);
        txtadvance=findViewById(R.id.txtadvance);

        reject=findViewById(R.id.reject);
        approve=findViewById(R.id.approve);
      setTitle();
      back();
        //getSupportActionBar().setTitle("Request");
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

        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postStatus("ACCEPT");
                finish();
            }
        });

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postStatus("REJECT");
                finish();
            }
        });


    }

    private void postStatus(String status)
    {
        LoginModel model = sh.getLoginModel("LOGIN_MODEL");
        Singleton.getInstance().getApi().postAcceptRejectPendings(model.getId(),prerequestmodel.getId(),status).enqueue(new Callback<PreRequestResMeta>() {
            @Override
            public void onResponse(Call<PreRequestResMeta> call, Response<PreRequestResMeta> response) {

            }

            @Override
            public void onFailure(Call<PreRequestResMeta> call, Throwable t) {

            }
        });
    }
    private void setTitle()
    {
        TextView title= (TextView) findViewById(R.id.title);
        title.setText(getString(R.string.pending_details));
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
