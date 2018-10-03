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
import com.mmcs.trackapp.adaptor.FeedbackListAdaptor;
import com.mmcs.trackapp.adaptor.PreRequestAdaptor;
import com.mmcs.trackapp.model.FeedbackModel;
import com.mmcs.trackapp.model.FeedbackResMeta;
import com.mmcs.trackapp.model.LoginModel;
import com.mmcs.trackapp.model.PreRequestModel;
import com.mmcs.trackapp.model.PreRequestResMeta;
import com.mmcs.trackapp.model.ResMetaUsers;
import com.mmcs.trackapp.util.Shprefrences;
import com.mmcs.trackapp.util.Singleton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedbackListActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    ArrayList<PreRequestModel> list;
    ListView listView;
    ProgressBar progress;
    RelativeLayout txtAdd;
    Shprefrences sh;
    ArrayList<FeedbackModel> feedbackModels;
    FeedbackListAdaptor feedbackListAdaptor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pre_request_activity);
        listView = findViewById(R.id.listView);
        progress = findViewById(R.id.progress);
        txtAdd = findViewById(R.id.txtAdd);
        progress.setVisibility(View.VISIBLE);
        SearchView editTextName=(SearchView) findViewById(R.id.edt);
        editTextName.setQueryHint(getString(R.string.search_here));
        editTextName.setOnQueryTextListener(this);
        //getSupportActionBar().setTitle("Pre-Requests");
        sh = new Shprefrences(this);
        back();
        setTitle();
        txtAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FeedbackListActivity.this, FeedbackActivity.class));
            }
        });
       /* listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PreRequestAdaptor adapter = (PreRequestAdaptor) adapterView.getAdapter();
                PreRequestModel preRequestModel = adapter.list.get(i);
                Intent intent = new Intent(FeedbackListActivity.this, PreRequestDetailActivity.class);
                intent.putExtra(getString(R.string.prerequest_model), preRequestModel);
                startActivity(intent);
            }
        });*/
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
    protected void onResume() {
        super.onResume();
        progress.setVisibility(View.VISIBLE);
        getFeedbackList();
    }


    private void setTitle() {
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(getString(R.string.feedback));
    }


    private void getFeedbackList()
    {
        LoginModel model = sh.getLoginModel(getString(R.string.login_model));
        Singleton.getInstance().getApi().getFeedbackList(model.getId()).enqueue(new Callback<FeedbackResMeta>() {
            @Override
            public void onResponse(Call<FeedbackResMeta> call, Response<FeedbackResMeta> response) {
                if (response.body() != null) {
                    feedbackModels=response.body().getResponse();
                    feedbackListAdaptor=new FeedbackListAdaptor(FeedbackListActivity.this,feedbackModels);
                    listView.setAdapter(feedbackListAdaptor);
                    listView.setEmptyView(findViewById(R.id.imz_nodata));
                    progress.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<FeedbackResMeta> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        s=s.toLowerCase();
        ArrayList<FeedbackModel> newlist=new ArrayList<>();
        for(FeedbackModel filterlist:feedbackModels)
        {

            String cust_name =filterlist.getCustomer_id().toLowerCase();
            String text_feedback =filterlist.getFeedback().toLowerCase();
            String created_on =filterlist.getCreated_on().toLowerCase();

            if(cust_name.contains(s)||text_feedback.contains(s)|| created_on.contains(s)) {
                newlist.add(filterlist);
            }
        }
        feedbackListAdaptor.filter(newlist);
        return true;
    }
}

