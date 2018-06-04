package com.example.lenovo.trackapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.lenovo.trackapp.R;
import com.example.lenovo.trackapp.adaptor.CustomerDetailListAdapter;
import com.example.lenovo.trackapp.model.CustomerModel;
import com.example.lenovo.trackapp.model.ResMetaCustomer;
import com.example.lenovo.trackapp.util.Singleton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCustomerActivity extends AppCompatActivity  implements SearchView.OnQueryTextListener{
    RecyclerView recyclerView;
    List<CustomerModel> clientDetailList = new ArrayList<>();
    TextView selectClientList;
    RelativeLayout progressLayout;
    ProgressDialog progressDialog;
    ProgressBar progress;
    CustomerDetailListAdapter manageClientsListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);
        SearchView editText=(SearchView) findViewById(R.id.edt);
       RelativeLayout addicon=findViewById(R.id.txtAdd);
        SearchView editTextName=(SearchView) findViewById(R.id.edt);
        progress = findViewById(R.id.progress);

        progress.setVisibility(View.VISIBLE);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        editTextName.setQueryHint("Search By Customer Name ");
        editTextName.setOnQueryTextListener(this);
        getSupportActionBar().setTitle("Customer Details");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AddCustomerActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
       // editText.setOnQueryTextListener(this);
        addicon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
               Intent intent=new Intent(AddCustomerActivity.this,NewCustomerActivity.class);
                startActivity(intent);
            }
        });
        getCustomerList();
    }
    public void getCustomerList() {
        Singleton.getInstance().getApi().getCustomerList("").enqueue(new Callback<ResMetaCustomer>() {
            @Override
            public void onResponse(Call<ResMetaCustomer> call, Response<ResMetaCustomer> response) {

                clientDetailList=response.body().getResponse();
                manageClientsListAdapter = new CustomerDetailListAdapter(AddCustomerActivity.this, clientDetailList);
                recyclerView.setAdapter(manageClientsListAdapter);
                progress.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(Call<ResMetaCustomer> call, Throwable t) {
                Log.e("**Error**", t.getMessage());
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
        ArrayList<CustomerModel>newlist=new ArrayList<>();
        for(CustomerModel name:clientDetailList)
        {
            String getName=name.getCustomer_name().toLowerCase();
            if(getName.contains(s)){
                newlist.add(name);
            }
        }
        manageClientsListAdapter.filter(newlist);
        return true;
    }
}

