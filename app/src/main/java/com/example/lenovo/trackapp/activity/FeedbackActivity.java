package com.example.lenovo.trackapp.activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.trackapp.R;
import com.example.lenovo.trackapp.adaptor.CustomerPopupAdaptor;
import com.example.lenovo.trackapp.model.CustomerModel;
import com.example.lenovo.trackapp.model.ResMetaCustomer;
import com.example.lenovo.trackapp.util.Singleton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedbackActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    EditText edtCustomer,comment;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        edtCustomer = findViewById(R.id.edtCustomer);
        comment=(EditText)findViewById(R.id.edt_comment);
        submit=(Button) findViewById(R.id.btnSubmit);
        getSupportActionBar().setTitle("FeedbackActivity");
        edtCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomerPopup();
            }
        });
        getCustomerList();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
             String customer_name=edtCustomer.getText().toString();
             String comm=comment.getText().toString();
             if(customer_name.equals("")){
                 Toast.makeText(FeedbackActivity.this,"Enter Select Customer Name",Toast.LENGTH_SHORT).show();
             }
             else if(comm.equals("")) {
                 Toast.makeText(FeedbackActivity.this, "Enter Your Comment", Toast.LENGTH_SHORT).show();
             }
             else{
                 Toast.makeText(FeedbackActivity.this,"Saved",Toast.LENGTH_SHORT).show();
             }
            }
        });
    }
    ArrayList<CustomerModel> listCustomer;
    CustomerPopupAdaptor customerPopupAdaptor;
    android.app.AlertDialog alertDialog;
    String customerid;
    private void showCustomerPopup() {
        customerPopupAdaptor = new CustomerPopupAdaptor(FeedbackActivity.this, listCustomer);
         android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(this);
        // ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.meeting_popup, null);
        final ListView listPurpose = dialogView.findViewById(R.id.listPurpose);
        TextView title = dialogView.findViewById(R.id.title);
        final SearchView editTextName = dialogView.findViewById(R.id.edt);
        editTextName.setQueryHint("Search Here");
        editTextName.setOnQueryTextListener(this);
        title.setText("Select Customer");
        //Button btnUpgrade = (Button) dialogView.findViewById(R.id.btnUpgrade);
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        alertDialog.show();
        listPurpose.setAdapter(customerPopupAdaptor);
        listPurpose.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                CustomerModel obj = (CustomerModel) listPurpose.getAdapter().getItem(position);
                edtCustomer.setText(obj.getCustomer_name());
                customerid=obj.getId();
                alertDialog.dismiss();
            }
        });

    }
    public void getCustomerList() {
        Singleton.getInstance().getApi().getCustomerList("").enqueue(new Callback<ResMetaCustomer>() {
            @Override
            public void onResponse(Call<ResMetaCustomer> call, Response<ResMetaCustomer> response) {

                listCustomer = response.body().getResponse();
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
        s = s.toLowerCase();
        ArrayList<CustomerModel> newlist1 = new ArrayList<>();
                for (CustomerModel list : listCustomer) {
                    String getCustomer = list.getCustomer_name().toLowerCase();
                    if (getCustomer.contains(s)) {
                        newlist1.add(list);
                    }
                }
                customerPopupAdaptor.filter(newlist1);
        return false;
    }
}


