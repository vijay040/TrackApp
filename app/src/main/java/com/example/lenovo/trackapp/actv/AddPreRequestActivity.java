package com.example.lenovo.trackapp.actv;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.lenovo.trackapp.R;
import com.example.lenovo.trackapp.adaptor.CustomerPopupAdaptor;
import com.example.lenovo.trackapp.adaptor.PurposePopupAdaptor;
import com.example.lenovo.trackapp.model.CustomerModel;
import com.example.lenovo.trackapp.model.PurposeModel;
import com.example.lenovo.trackapp.model.ResMetaCustomer;
import com.example.lenovo.trackapp.model.ResponseMeta;
import com.example.lenovo.trackapp.util.Singleton;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Lenovo on 22-05-2018.
 */

public class AddPreRequestActivity extends AppCompatActivity {

    EditText edtMeetings, edtDescreption, edtRequestType, edtAdvance;
    Button btnSubmit;
    ProgressBar progress;

    //SweetAlertDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_prerequest_activity);
        edtMeetings = findViewById(R.id.edtMeetings);
        edtDescreption = findViewById(R.id.edtDescreption);
        edtRequestType = findViewById(R.id.edtRequestType);
        edtAdvance = findViewById(R.id.edtAdvance);
        btnSubmit = findViewById(R.id.btnSubmit);
        progress = findViewById(R.id.progress);
        getPurposeList();
        getCustomerList();
        edtMeetings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMeetings();
            }
        });
        edtRequestType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRequestType();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }


    public void getPurposeList() {
        Singleton.getInstance().getApi().getPurposeList("").enqueue(new Callback<ResponseMeta>() {
            @Override
            public void onResponse(Call<ResponseMeta> call, Response<ResponseMeta> response) {

                purposeList = response.body().getResponse();
            }

            @Override
            public void onFailure(Call<ResponseMeta> call, Throwable t) {

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


    AlertDialog alertDialog;
    ArrayList<PurposeModel> purposeList = new ArrayList<>();
    PurposePopupAdaptor adaptor;

    private void showMeetings() {

        adaptor = new PurposePopupAdaptor(com.example.lenovo.trackapp.actv.AddPreRequestActivity.this, purposeList);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        // ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.meeting_popup, null);
        final ListView listPurpose = dialogView.findViewById(R.id.listPurpose);
        //Button btnUpgrade = (Button) dialogView.findViewById(R.id.btnUpgrade);
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        alertDialog.show();
        listPurpose.setAdapter(adaptor);

        listPurpose.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                PurposeModel obj = (PurposeModel) listPurpose.getAdapter().getItem(position);
                edtMeetings.setText(obj.getPurpose());
                alertDialog.dismiss();
            }
        });

    }

    ArrayList<CustomerModel> listCustomer;

    private void showRequestType() {
        CustomerPopupAdaptor adapto = new CustomerPopupAdaptor(com.example.lenovo.trackapp.actv.AddPreRequestActivity.this, listCustomer);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        // ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.meeting_popup, null);
        final ListView listPurpose = dialogView.findViewById(R.id.listPurpose);
        TextView title = dialogView.findViewById(R.id.title);
        title.setText("Select Customer");
        //Button btnUpgrade = (Button) dialogView.findViewById(R.id.btnUpgrade);
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        alertDialog.show();
        listPurpose.setAdapter(adapto);

        listPurpose.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                CustomerModel obj = (CustomerModel) listPurpose.getAdapter().getItem(position);
                edtRequestType.setText(obj.getCustomer_name());
                alertDialog.dismiss();
            }
        });

    }


}
