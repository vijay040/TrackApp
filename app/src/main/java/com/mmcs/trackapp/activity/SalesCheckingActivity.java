package com.mmcs.trackapp.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.mmcs.trackapp.R;
import com.mmcs.trackapp.adaptor.CustomerPopupAdaptor;
import com.mmcs.trackapp.adaptor.MeetingDetailsAdapter;
import com.mmcs.trackapp.adaptor.PurposePopupAdaptor;
import com.mmcs.trackapp.model.CustomerModel;
import com.mmcs.trackapp.model.ExpenseResMeta;
import com.mmcs.trackapp.model.LoginModel;
import com.mmcs.trackapp.model.MeetingModel;
import com.mmcs.trackapp.model.PortResMeta;
import com.mmcs.trackapp.model.PurposeModel;
import com.mmcs.trackapp.model.ResMetaCustomer;
import com.mmcs.trackapp.model.ResMetaMeeting;
import com.mmcs.trackapp.model.ResponseMeta;
import com.mmcs.trackapp.util.Shprefrences;
import com.mmcs.trackapp.util.Singleton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SalesCheckingActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{
    TextView txt_next;
ListView listvendor_details;
EditText edt_port_loading,edt_port_destination;
    ProgressBar progress;
    Shprefrences sh;
    ProgressBar progressBar;
    ArrayList<MeetingModel>  meetinglist = new ArrayList<>();
    MeetingDetailsAdapter meetingadapter;
    LoginModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_checking);
        sh = new Shprefrences(this);
        model=sh.getLoginModel(getResources().getString(R.string.login_model));
        txt_next=findViewById(R.id.txt_next);
        edt_port_loading=findViewById(R.id.edt_port_loading);
        edt_port_destination=findViewById(R.id.edt_port_destination);
        listvendor_details=findViewById(R.id.listvendor_details);
        setTitle();
        back();
        getPOLAndPOD();
        progressBar = findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);
        listvendor_details.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MeetingDetailsAdapter adapter = (MeetingDetailsAdapter) adapterView.getAdapter();
                MeetingModel model = adapter.list.get(i);
                Intent intent = new Intent(SalesCheckingActivity.this, VendorDetailsActivity.class);
                intent.putExtra(getString(R.string.meeting_model), model);
                startActivity(intent);
            }
        });
        txt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pol=edt_port_loading.getText().toString();
                String pod=edt_port_destination.getText().toString();
                if(pol.equals(""))
                {
                    Toast.makeText(SalesCheckingActivity.this,"select port of loading",Toast.LENGTH_SHORT).show();
                }
              else if(pod.equals(""))
                {
                    Toast.makeText(SalesCheckingActivity.this,"select port of Destination",Toast.LENGTH_SHORT).show();
                }
                else
                    listvendor_details.setVisibility(View.VISIBLE);
            }
        });
        edt_port_loading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPolPopup();
            }
        });
        edt_port_destination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPODPopup();
            }
        });

    }



    private void getPOLAndPOD()
    {
        Singleton.getInstance().getApi().getPOLAndPOD(model.getId()).enqueue(new Callback<PortResMeta>() {
            @Override
            public void onResponse(Call<PortResMeta> call, Response<PortResMeta> response) {

            }

            @Override
            public void onFailure(Call<PortResMeta> call, Throwable t) {

            }
        });
    }

    AlertDialog alertDialog;
    ArrayList<CustomerModel> listCustomer;
    CustomerPopupAdaptor customerPopupAdaptor;
    String customerid;
    private int popupId = 0;
    private void showPolPopup() {

        customerPopupAdaptor = new CustomerPopupAdaptor(SalesCheckingActivity.this, listCustomer);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        // ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.meeting_popup, null);
        final ListView listPurpose = dialogView.findViewById(R.id.listPurpose);
        TextView title = dialogView.findViewById(R.id.title);
        final SearchView editTextName = dialogView.findViewById(R.id.edt);
        editTextName.setQueryHint(getString(R.string.search_here));
        editTextName.setOnQueryTextListener(this);
        title.setText(getString(R.string.select_customer));
        //Button btnUpgrade = (Button) dialogView.findViewById(R.id.btnUpgrade);
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        popupId = 1;
        alertDialog.show();
        listPurpose.setAdapter(customerPopupAdaptor);

        listPurpose.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                CustomerModel obj = (CustomerModel) listPurpose.getAdapter().getItem(position);
                edt_port_loading.setText(obj.getCustomer_name());
                customerid = obj.getId();
                alertDialog.dismiss();
            }
        });

    }
    ArrayList<PurposeModel> purposeList = new ArrayList<>();
    PurposePopupAdaptor purposePopupAdaptor;


    private void showPODPopup() {
       /* PurposeModel m=new PurposeModel();
        m.setPurpose("Business Meeting in Noida");
        m.setId("0");

        purposeList.add(m);*/
        purposePopupAdaptor = new PurposePopupAdaptor(SalesCheckingActivity.this, purposeList);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        // ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.meeting_popup, null);
        final ListView listPurpose = dialogView.findViewById(R.id.listPurpose);
        //Button btnUpgrade = (Button) dialogView.findViewById(R.id.btnUpgrade);
        final SearchView editTextName = dialogView.findViewById(R.id.edt);
        editTextName.setQueryHint(getString(R.string.search_here));
        editTextName.setOnQueryTextListener(this);
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        alertDialog.show();
        popupId = 2;
        listPurpose.setAdapter(purposePopupAdaptor);

        listPurpose.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                PurposeModel obj = (PurposeModel) listPurpose.getAdapter().getItem(position);
                edt_port_destination.setText(obj.getPurpose());
                alertDialog.dismiss();
            }
        });

    }

    private void setTitle() {
        TextView title = (TextView) findViewById(R.id.title);
        title.setText("SALES CHECKING");
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
        switch (popupId) {
            case 1:
                ArrayList<CustomerModel> newlist1 = new ArrayList<>();
                for (CustomerModel list : listCustomer) {
                    String getCustomer = list.getCustomer_name().toLowerCase();
                    if (getCustomer.contains(s)) {
                        newlist1.add(list);
                    }
                }
                customerPopupAdaptor.filter(newlist1);
                break;
            case 2:
                ArrayList<PurposeModel> newlist = new ArrayList<>();
                for (PurposeModel list : purposeList) {
                    String getPurpose = list.getPurpose().toLowerCase();

                    if (getPurpose.contains(s)) {
                        newlist.add(list);
                    }
                }
                purposePopupAdaptor.filter(newlist);
                break;
        }
        return false;
    }
    public void getMeetingList() {
        LoginModel model = sh.getLoginModel(getString(R.string.login_model));
        Singleton.getInstance().getApi().getMeetingsList("" + model.getId()).enqueue(new Callback<ResMetaMeeting>() {
            @Override
            public void onResponse(Call<ResMetaMeeting> call, Response<ResMetaMeeting> response) {
                if(response.body()!=null) {
                    meetinglist = response.body().getResponse();
                    meetingadapter = new MeetingDetailsAdapter(SalesCheckingActivity.this, meetinglist);
                    listvendor_details.setAdapter(meetingadapter);

                  progressBar.setVisibility(View.GONE);
                }
             progressBar.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(Call<ResMetaMeeting> call, Throwable throwable) {
               progressBar.setVisibility(View.GONE);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        getMeetingList();
    }

}
