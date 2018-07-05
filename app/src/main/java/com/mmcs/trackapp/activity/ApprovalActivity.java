package com.mmcs.trackapp.activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mmcs.trackapp.R;
import com.mmcs.trackapp.adaptor.PreRequestAdaptor;
import com.mmcs.trackapp.model.LoginModel;
import com.mmcs.trackapp.model.PreRequestModel;
import com.mmcs.trackapp.model.PreRequestResMeta;
import com.mmcs.trackapp.util.Shprefrences;
import com.mmcs.trackapp.util.Singleton;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class ApprovalActivity extends AppCompatActivity {
    ArrayList<PreRequestModel> list;
    ListView listView;
    ProgressBar progress;
    Shprefrences sh;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approval);
        listView = findViewById(R.id.listView);
        progress = findViewById(R.id.progress);
        progress.setVisibility(View.VISIBLE);

        //getSupportActionBar().setTitle("Approvals");
        sh=new Shprefrences(this);
        back();
        setTitle();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){
                PreRequestAdaptor adapter = (PreRequestAdaptor) adapterView.getAdapter();
                PreRequestModel preRequestModel = adapter.list.get(i);
                Intent intent = new Intent(ApprovalActivity.this, PreRequestDetailActivity.class);
                intent.putExtra("PREREQUESTMODEL", preRequestModel);
                startActivity(intent);
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
        title.setText(getString(R.string.Approvals));
    }
    @Override
    protected void onResume(){
        super.onResume();
        getPreRequestList();
    }
    private void getPreRequestList(){
        LoginModel model = sh.getLoginModel(getString(R.string.login_model));
        Singleton.getInstance().getApi().getPrerequestMeetingList(model.getId()).enqueue(new Callback<PreRequestResMeta>(){
            @Override
            public void onResponse(Call<PreRequestResMeta> call, Response<PreRequestResMeta> response){
                list = response.body().getResponse();
                PreRequestAdaptor adaptor = new PreRequestAdaptor(ApprovalActivity.this, list);
                listView.setAdapter(adaptor);
                listView.setEmptyView(findViewById(R.id.txt_nodata));
                progress.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(Call<PreRequestResMeta> call, Throwable t){
                Log.e("**Error**", t.getMessage());
                progress.setVisibility(View.GONE);
                }
                });
                }
                }

