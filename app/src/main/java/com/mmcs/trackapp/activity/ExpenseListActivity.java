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
import android.widget.SearchView;
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

public class ExpenseListActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{
    ListView listExpenseView;
    ProgressBar progressBar;
    RelativeLayout txtAdd;
    ArrayList<ExpenseModel> expenseModels=new ArrayList();
    Shprefrences sh;
    ExpenseListAdaptor expenseListAdaptor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_list);
        listExpenseView=findViewById(R.id.listExpenseView);
        progressBar=findViewById(R.id.progress);
        SearchView editTextName=(SearchView) findViewById(R.id.edt);
        editTextName.setQueryHint(getString(R.string.search_here));
        editTextName.setOnQueryTextListener(this);
        //getSupportActionBar().setTitle("Expenses");
        txtAdd=findViewById(R.id.txtAdd);
        sh=new Shprefrences(this);
        back();
        setTitle();
        txtAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ExpenseListActivity.this,NewExpenseActivity.class));
            }
        });
        listExpenseView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ExpenseListAdaptor adapter = (ExpenseListAdaptor) adapterView.getAdapter();
                ExpenseModel model = adapter.list.get(i);
                Intent intent = new Intent(ExpenseListActivity.this, ExpenseDetailActivity.class);
                intent.putExtra(getString(R.string.expense_model), model);
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
        LoginModel model = sh.getLoginModel(getString(R.string.login_model));
        Singleton.getInstance().getApi().getExpanseList(model.getId()).enqueue(new Callback<ExpenseResMeta>() {
            @Override
            public void onResponse(Call<ExpenseResMeta> call, Response<ExpenseResMeta> response) {

                if(response.body()==null)
                    return;
                expenseModels=response.body().getResponse();
                expenseListAdaptor=new ExpenseListAdaptor(ExpenseListActivity.this,expenseModels);
                listExpenseView.setAdapter(expenseListAdaptor);
                listExpenseView.setEmptyView(findViewById(R.id.imz_nodata));
                progressBar.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(Call<ExpenseResMeta> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }
    private void setTitle()
    {
        TextView title= (TextView) findViewById(R.id.title);
        title.setText(getString(R.string.expense_list));
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        s=s.toLowerCase();
        ArrayList<ExpenseModel> newlist=new ArrayList<>();
        for(ExpenseModel filterlist:expenseModels)
        {
            String des=filterlist.getDescreption().toLowerCase();
            String cust_name =filterlist.getCustomer_name().toLowerCase();
            String address =filterlist.getAddress().toLowerCase();
            String created_on =filterlist.getCreated_on().toLowerCase();
            String adv =filterlist.getAmount().toLowerCase();
            String date =filterlist.getDate().toLowerCase();
            if(des.contains(s)||cust_name.contains(s)||address.contains(s)|| created_on.contains(s)|| adv.contains(s)|| date.contains(s)) {
                newlist.add(filterlist);
            }
        }
        expenseListAdaptor.filter(newlist);
        return true;
    }
}
