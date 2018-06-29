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
import android.widget.TextView;
import android.widget.Toast;

import com.mmcs.trackapp.R;
import com.mmcs.trackapp.adaptor.ExpenseListAdaptor;
import com.mmcs.trackapp.model.ExpenseModel;
import com.mmcs.trackapp.model.ExpenseResMeta;
import com.mmcs.trackapp.model.LoginModel;
import com.mmcs.trackapp.util.Shprefrences;
import com.mmcs.trackapp.util.Singleton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExpenseListActivity extends AppCompatActivity {
    ListView listExpenseView;
    ProgressBar progressBar;
    RelativeLayout txtAdd;
    Shprefrences sh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_list);
        listExpenseView=findViewById(R.id.listExpenseView);
        progressBar=findViewById(R.id.progress);
        //getSupportActionBar().setTitle("Expenses");
        txtAdd=findViewById(R.id.txtAdd);

        sh=new Shprefrences(this);
        back();
        txtAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ExpenseListActivity.this,NewExpenseActivity.class));
            }
        });
        listExpenseView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ExpenseListAdaptor adaptor = (ExpenseListAdaptor) adapterView.getAdapter();
                ExpenseModel model = adaptor.list.get(i);
                Intent intent = new Intent(ExpenseListActivity.this, ExpenseDetailActivity.class);
                intent.putExtra("EXPENSEMODEL", model);
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
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.VISIBLE);
        getExpenseList();
    }
    private void getExpenseList()
    {
        LoginModel model = sh.getLoginModel("LOGIN_MODEL");
        Singleton.getInstance().getApi().getExpanseList(model.getId()).enqueue(new Callback<ExpenseResMeta>() {
            @Override
            public void onResponse(Call<ExpenseResMeta> call, Response<ExpenseResMeta> response) {
                ArrayList<ExpenseModel> model=response.body().getResponse();
                ExpenseListAdaptor adaptor=new ExpenseListAdaptor(ExpenseListActivity.this,model);
                listExpenseView.setAdapter(adaptor);
                progressBar.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(Call<ExpenseResMeta> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
