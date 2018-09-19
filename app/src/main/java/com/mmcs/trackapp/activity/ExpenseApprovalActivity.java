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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mmcs.trackapp.R;
import com.mmcs.trackapp.adaptor.ExpApprovalAdaptor;
import com.mmcs.trackapp.adaptor.PendingAdaptor;
import com.mmcs.trackapp.model.ExpResListMeta;
import com.mmcs.trackapp.model.ExpenseApprovalListModel;
import com.mmcs.trackapp.model.LoginModel;
import com.mmcs.trackapp.model.PreRequestModel;
import com.mmcs.trackapp.model.PreRequestResMeta;
import com.mmcs.trackapp.util.Shprefrences;
import com.mmcs.trackapp.util.Singleton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExpenseApprovalActivity extends AppCompatActivity {

    ArrayList<ExpenseApprovalListModel> list;
    ListView listView;
    ProgressBar progress;
    Shprefrences sh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_approval);
        listView = findViewById(R.id.listView);
        progress = findViewById(R.id.progress);
        progress.setVisibility(View.VISIBLE);
        // getSupportActionBar().setTitle("Request List");
        sh=new Shprefrences(this);
        getExpenseApprovalsList();
        back();
        setTitle();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ExpApprovalAdaptor adapter = (ExpApprovalAdaptor) adapterView.getAdapter();
                ExpenseApprovalListModel expenseApprovalListModel = adapter.list.get(i);
                Intent intent = new Intent(ExpenseApprovalActivity.this, ExpenseAppDetailActivity.class);
                intent.putExtra(getString(R.string.expense_appro_model), expenseApprovalListModel);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onResume(){
        super.onResume();
        //getPendingsList();
        getExpenseApprovalsList();
    }
   /* private void getPendingsList(){
        LoginModel model = sh.getLoginModel(getString(R.string.login_model));
        Singleton.getInstance().getApi().getPendingsList(model.getId()).enqueue(new Callback<PreRequestResMeta>() {
            @Override
            public void onResponse(Call<PreRequestResMeta> call, Response<PreRequestResMeta> response) {
                list = response.body().getResponse();
                PendingAdaptor adaptor = new PendingAdaptor(ExpenseApprovalActivity.this, list);
                listView.setAdapter(adaptor);
                listView.setEmptyView(findViewById(R.id.imz_nodata));
                progress.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(Call<PreRequestResMeta> call, Throwable t) {
                Log.e("**Error**", t.getMessage());
                progress.setVisibility(View.GONE);
            }
        });
    }*/
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
        title.setText(getString(R.string.expense_approval));
    }


    private  void getExpenseApprovalsList()
    {
        LoginModel model=sh.getLoginModel(getString(R.string.login_model));
        Singleton.getInstance().getApi().getExpenseApprovalsList(model.getId()).enqueue(new Callback<ExpResListMeta>() {
            @Override
            public void onResponse(Call<ExpResListMeta> call, Response<ExpResListMeta> response) {
                list = response.body().getResponse();
                ExpApprovalAdaptor adaptor = new ExpApprovalAdaptor(ExpenseApprovalActivity.this, list);
                listView.setAdapter(adaptor);
                listView.setEmptyView(findViewById(R.id.imz_nodata));
                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ExpResListMeta> call, Throwable t) {

            }
        });
    }

}
