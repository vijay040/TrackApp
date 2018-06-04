package com.example.lenovo.trackapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.trackapp.db.Cons;
import com.example.lenovo.trackapp.model.CustomerDetails;
import com.example.lenovo.trackapp.db.HttpCallBack;
import com.example.lenovo.trackapp.db.HttpGetRequest;
import com.example.lenovo.trackapp.R;
import com.example.lenovo.trackapp.adaptor.CustomerDetailListAdapter;
import com.example.lenovo.trackapp.model.CustomerModel;
import com.example.lenovo.trackapp.model.ResMetaCurrency;
import com.example.lenovo.trackapp.model.ResMetaCustomer;
import com.example.lenovo.trackapp.model.ResMetaCustomerList;
import com.example.lenovo.trackapp.util.Singleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    CustomerDetailListAdapter manageClientsListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);
        SearchView editText=(SearchView) findViewById(R.id.edt);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.drawable.ic_plusicon);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        getSupportActionBar().setTitle("Customer Details");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AddCustomerActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
       // editText.setOnQueryTextListener(this);
        fab.setOnClickListener(new View.OnClickListener(){
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
            }

            @Override
            public void onFailure(Call<ResMetaCustomer> call, Throwable t) {

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

