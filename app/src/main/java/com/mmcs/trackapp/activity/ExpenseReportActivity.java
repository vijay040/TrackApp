package com.mmcs.trackapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.mmcs.trackapp.R;
import com.mmcs.trackapp.adaptor.ReportAdapter;
import com.mmcs.trackapp.model.LoginModel;
import com.mmcs.trackapp.model.ReportModel;
import com.mmcs.trackapp.model.ReportResMeta;
import com.mmcs.trackapp.model.ResMetaReqTypes;
import com.mmcs.trackapp.util.Shprefrences;
import com.mmcs.trackapp.util.Singleton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExpenseReportActivity extends AppCompatActivity {
    ListView listExpenseReport;
    ProgressBar progressBar;
Shprefrences sh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        sh=new Shprefrences(this);
        setContentView(R.layout.activity_expense_report);
        listExpenseReport = findViewById(R.id.listExpenseReport);
        back();
        progressBar = findViewById(R.id.progress);
        getReportList();
        listExpenseReport.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ReportAdapter adap = (ReportAdapter) adapterView.getAdapter();
                ReportModel model = adap.list.get(i);
                Intent intent = new Intent(ExpenseReportActivity.this, ExpenseReportDetailsActivity.class);
                intent.putExtra("REPORTMODEL", model);
                startActivity(intent);
            }
        });
    }

    public void getReportList()
    {
        LoginModel model = sh.getLoginModel("LOGIN_MODEL");
        Singleton.getInstance().getApi().getReportList(model.getId()).enqueue(new Callback<ReportResMeta>() {
            @Override
            public void onResponse(Call<ReportResMeta> call, Response<ReportResMeta> response) {

                ArrayList<ReportModel> model=response.body().getResponse();
                ReportAdapter adap=new ReportAdapter(ExpenseReportActivity.this,model);
                listExpenseReport.setAdapter(adap);

            }

            @Override
            public void onFailure(Call<ReportResMeta> call, Throwable t) {

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

}
