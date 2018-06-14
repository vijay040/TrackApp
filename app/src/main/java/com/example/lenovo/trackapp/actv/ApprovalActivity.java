package com.example.lenovo.trackapp.actv;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import com.example.lenovo.trackapp.R;
import com.example.lenovo.trackapp.activity.PreRequestDetailActivity;
import com.example.lenovo.trackapp.adaptor.PreRequestAdaptor;
import com.example.lenovo.trackapp.model.LoginModel;
import com.example.lenovo.trackapp.model.PreRequestModel;
import com.example.lenovo.trackapp.model.PreRequestResMeta;
import com.example.lenovo.trackapp.util.Shprefrences;
import com.example.lenovo.trackapp.util.Singleton;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class ApprovalActivity extends AppCompatActivity {
    ArrayList<PreRequestModel> list;
    ListView listView;
    ProgressBar progress;
    RelativeLayout txtAdd;
    Shprefrences sh;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pre_request_activity);
        listView = findViewById(R.id.listView);
        progress = findViewById(R.id.progress);
        txtAdd = findViewById(R.id.txtAdd);
        progress.setVisibility(View.VISIBLE);
        getSupportActionBar().setTitle("Pre-Requests");
        sh=new Shprefrences(this);
        txtAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ApprovalActivity.this,AddPreRequestActivity.class));
            }
            });
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
    @Override
    protected void onResume(){
        super.onResume();
        getPreRequestList();
    }
    private void getPreRequestList(){
        LoginModel model = sh.getLoginModel("LOGIN_MODEL");
        Singleton.getInstance().getApi().getPrerequestMeetingList(model.getId()).enqueue(new Callback<PreRequestResMeta>(){
            @Override
            public void onResponse(Call<PreRequestResMeta> call, Response<PreRequestResMeta> response){
                list = response.body().getResponse();
                PreRequestAdaptor adaptor = new PreRequestAdaptor(ApprovalActivity.this, list);
                listView.setAdapter(adaptor);
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

