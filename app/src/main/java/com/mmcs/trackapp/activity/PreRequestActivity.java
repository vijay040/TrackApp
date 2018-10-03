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
import android.widget.SearchView;
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

public class PreRequestActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    ArrayList<PreRequestModel> list;
    ListView listView;
    ProgressBar progress;
    RelativeLayout txtAdd;
    Shprefrences sh;
    PreRequestAdaptor preRequestAdaptor;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pre_request_activity);
        listView = findViewById(R.id.listView);
        progress = findViewById(R.id.progress);
        txtAdd = findViewById(R.id.txtAdd);
        SearchView editTextName=(SearchView) findViewById(R.id.edt);
        editTextName.setQueryHint(getString(R.string.search_here));
        editTextName.setOnQueryTextListener(this);
        progress.setVisibility(View.VISIBLE);
        //getSupportActionBar().setTitle("Pre-Requests");
        sh=new Shprefrences(this);
        back();
        setTitle();
        txtAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PreRequestActivity.this, AddPreRequestActivity.class));
            }
            });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PreRequestAdaptor adapter = (PreRequestAdaptor) adapterView.getAdapter();
                PreRequestModel preRequestModel = adapter.list.get(i);
                Intent intent = new Intent(PreRequestActivity.this, PreRequestDetailActivity.class);
                intent.putExtra(getString(R.string.prerequest_model), preRequestModel);
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

    @Override
    protected void onResume(){
        super.onResume();
        getPreRequestList();
    }
    private void getPreRequestList(){
        LoginModel model = sh.getLoginModel(getString(R.string.login_model));
        Singleton.getInstance().getApi().getPrerequestMeetingList(model.getId()).enqueue(new Callback<PreRequestResMeta>() {
            @Override
            public void onResponse(Call<PreRequestResMeta> call, Response<PreRequestResMeta> response) {
                if(response.body()!=null) {
                    list = response.body().getResponse();
                    preRequestAdaptor = new PreRequestAdaptor(PreRequestActivity.this, list);
                    listView.setAdapter(preRequestAdaptor);
                    listView.setEmptyView(findViewById(R.id.imz_nodata));
                    progress.setVisibility(View.GONE);
                }
                    progress.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(Call<PreRequestResMeta> call, Throwable t) {
                Log.e("**Error**", t.getMessage());
                progress.setVisibility(View.GONE);
                }
                });
                }
    private void setTitle()
    {
        TextView title= (TextView) findViewById(R.id.title);
        title.setText(getString(R.string.pre_requests));
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        s=s.toLowerCase();
        ArrayList<PreRequestModel> newlist=new ArrayList<>();
        for(PreRequestModel filterlist:list)
        {
            String des=filterlist.getComment().toLowerCase();
            String cust_name =filterlist.getCustomer_name().toLowerCase();
            String address =filterlist.getAddress().toLowerCase();
            String adv =filterlist.getAdvance().toLowerCase();
            String date =filterlist.getDate().toLowerCase();
            if(des.contains(s)||cust_name.contains(s)||address.contains(s)|| adv.contains(s)|| date.contains(s)) {
                newlist.add(filterlist);
            }
        }
        preRequestAdaptor.filter(newlist);
        return true;
    }
}

