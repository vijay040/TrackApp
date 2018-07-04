package com.mmcs.trackapp.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import com.mmcs.trackapp.R;
import com.mmcs.trackapp.adaptor.CustomerPopupAdaptor;
import com.mmcs.trackapp.model.CustomerModel;
import com.mmcs.trackapp.model.LoginModel;
import com.mmcs.trackapp.model.PreRequestResMeta;
import com.mmcs.trackapp.model.ResMetaCustomer;
import com.mmcs.trackapp.util.Shprefrences;
import com.mmcs.trackapp.util.Singleton;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedbackActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    EditText edtCustomer,comment;
    Button submit;
    Shprefrences sh;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        sh=new Shprefrences(this);
        setContentView(R.layout.activity_feedback);
        edtCustomer = findViewById(R.id.edtCustomer);
        comment=(EditText)findViewById(R.id.edt_comment);
        submit=(Button) findViewById(R.id.btnSubmit);
        progressBar=findViewById(R.id.progressbar);
        //getSupportActionBar().setTitle("Feedback");
        progressBar.setVisibility(View.VISIBLE);
        getCustomerList();
        back();
        setTitle();
        edtCustomer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                showCustomerPopup();
            }
        });
        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
             String customer_name=edtCustomer.getText().toString();
             String comm=comment.getText().toString();
             if(customer_name.equals("")){
                 Toast.makeText(FeedbackActivity.this,"Select Customer Name",Toast.LENGTH_SHORT).show();
             }
             else if(comm.equals("")) {
                 Toast.makeText(FeedbackActivity.this, "Enter Your Comment", Toast.LENGTH_SHORT).show();
             }
             else{
                 DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
                 final String createddate = df.format(Calendar.getInstance().getTime());
                 postFeedback(customerid,comm,createddate);
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
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
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
                progressBar.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(Call<ResMetaCustomer> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
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

    public void postFeedback(String customerId,String feedback, String posted_on)
    {
        LoginModel model = sh.getLoginModel("LOGIN_MODEL");
        progressBar.setVisibility(View.VISIBLE);
        Singleton.getInstance().getApi().postFeedback(model.getId(),customerId,feedback,posted_on).enqueue(new Callback<PreRequestResMeta>() {
            @Override
            public void onResponse(Call<PreRequestResMeta> call, Response<PreRequestResMeta> response) {
                Toast.makeText(FeedbackActivity.this,"Successfully posted",Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                finish();
                 }
            @Override
            public void onFailure(Call<PreRequestResMeta> call, Throwable t){
                progressBar.setVisibility(View.GONE);
            }
        });
    }
    private void setTitle()
    {
        TextView title= (TextView) findViewById(R.id.title);
        title.setText(getString(R.string.feedback));
    }

}


