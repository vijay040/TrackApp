package com.mmcs.trackapp.activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.mmcs.trackapp.R;
import com.mmcs.trackapp.adaptor.CustomerDetailListAdapter;
import com.mmcs.trackapp.model.CustomerModel;
import com.mmcs.trackapp.model.ResMetaCustomer;
import com.mmcs.trackapp.util.Singleton;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class AddCustomerActivity extends AppCompatActivity  implements SearchView.OnQueryTextListener{
    RecyclerView recyclerView;
    List<CustomerModel> clientDetailList = new ArrayList<>();
    ProgressBar progress;
    CustomerDetailListAdapter manageClientsListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
        back();
        setTitle();
       // getSupportActionBar().setTitle("Customer Details");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AddCustomerActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        addicon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
               Intent intent=new Intent(AddCustomerActivity.this,NewCustomerActivity.class);
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
    private void setTitle()
    {
       TextView title= (TextView) findViewById(R.id.title);
        title.setText(getString(R.string.customer_list));
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
    protected void onResume() {
        super.onResume();
        getCustomerList();
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }
    @Override
    public boolean onQueryTextChange(String s) {
        s=s.toLowerCase();
        ArrayList<CustomerModel> newlist=new ArrayList<>();
        for(CustomerModel name:clientDetailList)
        {
            String getName=name.getCustomer_name().toLowerCase();
            if(getName.contains(s)) {
                newlist.add(name);
            }
        }
        manageClientsListAdapter.filter(newlist);
        return true;
    }
}

