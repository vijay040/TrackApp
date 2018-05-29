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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AddCustomerActivity extends AppCompatActivity  implements SearchView.OnQueryTextListener{
    RecyclerView recyclerView;
    List<CustomerDetails> clientDetailList = new ArrayList<>();
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
        getMessageData();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AddCustomerActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        manageClientsListAdapter = new CustomerDetailListAdapter(AddCustomerActivity.this, clientDetailList);
        recyclerView.setAdapter(manageClientsListAdapter);
       // editText.setOnQueryTextListener(this);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
               Intent intent=new Intent(AddCustomerActivity.this,NewCustomerActivity.class);
                startActivity(intent);
            }
        });
    }
    private void getMessageData() {
        HttpGetRequest httpGetRequest = new HttpGetRequest(new HttpCallBack() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onPostSuccess(String response) {
                Log.e("Sann","aaa===="+response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray contacts = jsonObject.getJSONArray("response");
                    Log.e("Sann","ayay=="+contacts);
                    if (response != null) {
                        for (int i = 0; i < contacts.length(); i++) {
                            JSONObject c = contacts.getJSONObject(i);
                            CustomerDetails customerDetails = new CustomerDetails();
                            customerDetails.setName(c.getString("customer_name"));
                            customerDetails.setAddress(c.getString("address"));
                            customerDetails.setEmail(c.getString("email"));
                            customerDetails.setPhone(c.getString("phone"));
                            customerDetails.setTaxDetail(c.getString("tax_details"));
                            customerDetails.setCustomerId(c.getString("company_id"));
                            Log.e("Sann","ayay=="+c.getString("company_id"));
                            clientDetailList.add(customerDetails);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Server Problem Try After Some Time", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                manageClientsListAdapter = new CustomerDetailListAdapter(AddCustomerActivity.this, clientDetailList);
                recyclerView.setAdapter(manageClientsListAdapter);
                //  progressLayout.setVisibility(View.GONE);
               /* try {
                    if (dataManger != null)
                        dataManger.getAdminDetailDataManager().deleteAllClients();
                    if (clientDetailList != null)
                        clientDetailList.clear();

                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonOjbect = jsonArray.getJSONObject(i);
                        CustomerDetails adminDetail = new  AdminDetail;
                        if (clientDetailList != null)
                            clientDetailList.add(adminDetail);
                        if (dataManger != null)
                            dataManger.getAdminDetailDataManager().createOrUpdate(adminDetail);
                    }
                    if (clientsListAdapter != null)
                        clientsListAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (JsonParseException e) {
                    e.printStackTrace();
                } catch (JsonMappingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
            }
            @Override
            public void onPreExecute() {
            }
        });
        httpGetRequest.execute(Cons.GET_TOTAL_MESSAGE_BAL);
    }
    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }
    @Override
    public boolean onQueryTextChange(String s) {
        s=s.toLowerCase();
        ArrayList<CustomerDetails>newlist=new ArrayList<>();
        for(CustomerDetails name:clientDetailList)
        {
            String getName=name.getName().toLowerCase();
            if(getName.contains(s)){
                newlist.add(name);
            }
        }
        manageClientsListAdapter.filter(newlist);
        return true;
    }
}

