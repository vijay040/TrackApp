package com.mmcs.trackapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.mmcs.trackapp.R;
import com.mmcs.trackapp.adaptor.PendingAdaptor;
import com.mmcs.trackapp.model.LoginModel;
import com.mmcs.trackapp.model.PreRequestModel;
import com.mmcs.trackapp.model.PreRequestResMeta;
import com.mmcs.trackapp.util.Shprefrences;
import com.mmcs.trackapp.util.Singleton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendingActivity extends AppCompatActivity {
    ArrayList<PreRequestModel> list;
    ListView listView;
    ProgressBar progress;
    Shprefrences sh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending);
        listView = findViewById(R.id.listView);
        progress = findViewById(R.id.progress);
        progress.setVisibility(View.VISIBLE);
       // getSupportActionBar().setTitle("Request List");
        sh=new Shprefrences(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               PendingAdaptor adapter = (PendingAdaptor) adapterView.getAdapter();
                PreRequestModel preRequestModel = adapter.list.get(i);
                Intent intent = new Intent(PendingActivity.this, PendingDetailActivity.class);
                intent.putExtra("PREREQUESTMODEL", preRequestModel);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onResume(){
        super.onResume();
        getPendingsList();
    }
    private void getPendingsList(){
        LoginModel model = sh.getLoginModel("LOGIN_MODEL");
        Singleton.getInstance().getApi().getPendingsList(model.getId()).enqueue(new Callback<PreRequestResMeta>() {
            @Override
            public void onResponse(Call<PreRequestResMeta> call, Response<PreRequestResMeta> response) {
                list = response.body().getResponse();
                PendingAdaptor adaptor = new PendingAdaptor(PendingActivity.this, list);
                listView.setAdapter(adaptor);
                progress.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(Call<PreRequestResMeta> call, Throwable t) {
                Log.e("**Error**", t.getMessage());
                progress.setVisibility(View.GONE);
            }
        });
    }
}

