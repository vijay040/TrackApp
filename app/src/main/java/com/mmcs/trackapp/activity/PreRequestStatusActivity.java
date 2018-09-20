package com.mmcs.trackapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mmcs.trackapp.R;
import com.mmcs.trackapp.model.ExpResListMeta;
import com.mmcs.trackapp.model.PreReqUpdateModel;
import com.mmcs.trackapp.model.PreReqUpdateResMeta;
import com.mmcs.trackapp.util.Singleton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PreRequestStatusActivity extends AppCompatActivity {
TextView reqst_type,status;
Button btn_ok;
String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_request_status);
        reqst_type=findViewById(R.id.reqst_type);
        status=findViewById(R.id.status);
        btn_ok=findViewById(R.id.btn_ok);
        Bundle bundle = getIntent().getExtras();
        String text_request = bundle.getString("reqst_type");
        id= bundle.getString("id");
        reqst_type.setText("Request Type:"+text_request);

        getStatusDetails(id,text_request);
        back();
        setTitle();
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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
        title.setText(getString(R.string.status_detail));
    }

    private void getStatusDetails(String id,String type)
    {
        Singleton.getInstance().getApi().getPreRequestUpdate(id,type).enqueue(new Callback<PreReqUpdateResMeta>() {
            @Override
            public void onResponse(Call<PreReqUpdateResMeta> call, Response<PreReqUpdateResMeta> response) {
                PreReqUpdateModel model=response.body().getResponse().get(0);
                status.setText("Status:"+model.getStatus());
            }

            @Override
            public void onFailure(Call<PreReqUpdateResMeta> call, Throwable t) {

            }
        });
    }
}
