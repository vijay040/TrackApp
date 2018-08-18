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
import com.mmcs.trackapp.adaptor.PortAdapter;
import com.mmcs.trackapp.adaptor.PurposePopupAdaptor;
import com.mmcs.trackapp.adaptor.VQuotationAdapter;
import com.mmcs.trackapp.model.CustomerModel;
import com.mmcs.trackapp.model.ExpenseResMeta;
import com.mmcs.trackapp.model.LoginModel;
import com.mmcs.trackapp.model.MeetingModel;
import com.mmcs.trackapp.model.PortModel;
import com.mmcs.trackapp.model.PortResMeta;
import com.mmcs.trackapp.model.PurposeModel;
import com.mmcs.trackapp.model.ResMetaCustomer;
import com.mmcs.trackapp.model.ResMetaMeeting;
import com.mmcs.trackapp.model.ResponseMeta;
import com.mmcs.trackapp.model.VQuotationModel;
import com.mmcs.trackapp.model.VQuotationResMeta;
import com.mmcs.trackapp.util.Shprefrences;
import com.mmcs.trackapp.util.Singleton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SalesCheckingActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    TextView txt_next;
    ListView listvendor_details;
    EditText edt_port_loading, edt_port_destination;
    ProgressBar progress;
    Shprefrences sh;
    ProgressBar progressBar;
    ArrayList<MeetingModel> meetinglist = new ArrayList<>();
    MeetingDetailsAdapter meetingadapter;
    LoginModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_checking);
        sh = new Shprefrences(this);
        model = sh.getLoginModel(getResources().getString(R.string.login_model));
        txt_next = findViewById(R.id.txt_next);
        edt_port_loading = findViewById(R.id.edt_port_loading);
        edt_port_destination = findViewById(R.id.edt_port_destination);
        listvendor_details = findViewById(R.id.listvendor_details);
        setTitle();
        back();
        getPOLAndPOD();
        progressBar = findViewById(R.id.progress);
        txt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pol = edt_port_loading.getText().toString();
                String pod = edt_port_destination.getText().toString();
                if (pol.equals("")) {
                    Toast.makeText(SalesCheckingActivity.this, "select port of loading", Toast.LENGTH_SHORT).show();
                } else if (pod.equals("")) {
                    Toast.makeText(SalesCheckingActivity.this, "select port of Destination", Toast.LENGTH_SHORT).show();
                } else

                   getVQuotation(model.getId(),pol,pod);
            }
        });
        edt_port_loading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPortPopup_loading();

            }
        });
        edt_port_destination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPortPopup_Des();
            }
        });

    }

    private void getPOLAndPOD() {
        LoginModel model = sh.getLoginModel(getString(R.string.login_model));
        Singleton.getInstance().getApi().getPOLAndPOD(model.getId()).enqueue(new Callback<PortResMeta>() {
            @Override
            public void onResponse(Call<PortResMeta> call, Response<PortResMeta> response) {
                portList = response.body().getResponse();

            }

            @Override
            public void onFailure(Call<PortResMeta> call, Throwable t) {

            }
        });
    }

    ArrayList<VQuotationModel> vQModelList;
 VQuotationAdapter vQuotationAdapter;
    private void getVQuotation(String user_id,String pol,String pod)
    {
        Singleton.getInstance().getApi().getVQuotationList(user_id,pol,pod).enqueue(new Callback<VQuotationResMeta>() {
            @Override
            public void onResponse(Call<VQuotationResMeta> call, Response<VQuotationResMeta> response) {
                if(response.body()!=null) {
                    vQModelList = response.body().getResponse();
                    vQuotationAdapter = new VQuotationAdapter(SalesCheckingActivity.this, vQModelList);
                    listvendor_details.setAdapter(vQuotationAdapter);

                }

            }

            @Override
            public void onFailure(Call<VQuotationResMeta> call, Throwable t) {

            }
        });

    }

    /* ArrayList<CustomerModel> listCustomer;
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

    }*/
    AlertDialog alertDialog;
    ArrayList<PortModel> portList = new ArrayList<>();
    PortAdapter portAdapter;

    private void showPortPopup_loading() {
       /* PurposeModel m=new PurposeModel();
        m.setPurpose("Business Meeting in Noida");
        m.setId("0");

        purposeList.add(m);*/
        portAdapter = new PortAdapter(SalesCheckingActivity.this, portList);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        // ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.meeting_popup, null);
        final ListView listPurpose = dialogView.findViewById(R.id.listPurpose);
        //Button btnUpgrade = (Button) dialogView.findViewById(R.id.btnUpgrade);
        final SearchView editTextName = dialogView.findViewById(R.id.edt);
        TextView title=dialogView.findViewById(R.id.title);
        title.setText("SELECT POL");
        editTextName.setQueryHint(getString(R.string.search_here));
        editTextName.setOnQueryTextListener(this);
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        alertDialog.show();
        listPurpose.setAdapter(portAdapter);
        listPurpose.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                PortModel obj = (PortModel) listPurpose.getAdapter().getItem(position);
                edt_port_loading.setText(obj.getPort());
                alertDialog.dismiss();
            }
        });

    }

    private void showPortPopup_Des() {
       /* PurposeModel m=new PurposeModel();
        m.setPurpose("Business Meeting in Noida");
        m.setId("0");

        purposeList.add(m);*/
        portAdapter = new PortAdapter(SalesCheckingActivity.this, portList);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        // ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.meeting_popup, null);
        final ListView listPurpose = dialogView.findViewById(R.id.listPurpose);
        //Button btnUpgrade = (Button) dialogView.findViewById(R.id.btnUpgrade);
        final SearchView editTextName = dialogView.findViewById(R.id.edt);
        TextView title=dialogView.findViewById(R.id.title);
        title.setText("SELECT POD");
        editTextName.setQueryHint(getString(R.string.search_here));
        editTextName.setOnQueryTextListener(this);
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        alertDialog.show();
        listPurpose.setAdapter(portAdapter);
        listPurpose.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                PortModel obj = (PortModel) listPurpose.getAdapter().getItem(position);
                edt_port_destination.setText(obj.getPort());
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

        ArrayList<PortModel> newlist1 = new ArrayList<>();
        for (PortModel list : portList) {
            String getCustomer = list.getPort().toLowerCase();
            if (getCustomer.contains(s)) {
                newlist1.add(list);
            }
        }
        portAdapter.filter(newlist1);
        return false;
    }
}
