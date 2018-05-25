package com.example.lenovo.trackapp.actv;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
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
import com.example.lenovo.trackapp.adaptor.MeetingsAdaptor;
import com.example.lenovo.trackapp.adaptor.PurposePopupAdaptor;
import com.example.lenovo.trackapp.adaptor.RequestTypesAdaptor;
import com.example.lenovo.trackapp.model.CustomerModel;
import com.example.lenovo.trackapp.model.LoginModel;
import com.example.lenovo.trackapp.model.MeetingModel;
import com.example.lenovo.trackapp.model.PurposeModel;
import com.example.lenovo.trackapp.model.RequestTypeModel;
import com.example.lenovo.trackapp.model.ResMetaCustomer;
import com.example.lenovo.trackapp.model.ResMetaMeeting;
import com.example.lenovo.trackapp.model.ResMetaReqTypes;
import com.example.lenovo.trackapp.model.ResponseMeta;
import com.example.lenovo.trackapp.util.Shprefrences;
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
    Shprefrences sh;
    ArrayList<MeetingModel> meetingList;
    ArrayList<RequestTypeModel> requestTyoesList;
    ListView listTypes;

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
        listTypes = findViewById(R.id.listTypes);
        progress = findViewById(R.id.progress);
        sh = new Shprefrences(this);
        getMeetingsList();
        getReqestTypes();
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


    public void getMeetingsList() {
        LoginModel model = sh.getLoginModel("LOGIN_MODEL");
        Singleton.getInstance().getApi().getMeetingsList(model.getId()).enqueue(new Callback<ResMetaMeeting>() {
            @Override
            public void onResponse(Call<ResMetaMeeting> call, Response<ResMetaMeeting> response) {
                meetingList = response.body().getResponse();
            }

            @Override
            public void onFailure(Call<ResMetaMeeting> call, Throwable throwable) {

            }
        });
    }

    public void getReqestTypes() {
        LoginModel model = sh.getLoginModel("LOGIN_MODEL");
        Singleton.getInstance().getApi().getRequestTypes(model.getId()).enqueue(new Callback<ResMetaReqTypes>() {
            @Override
            public void onResponse(Call<ResMetaReqTypes> call, Response<ResMetaReqTypes> response) {
                requestTyoesList = response.body().getResponse();
                RequestTypesAdaptor adapto = new RequestTypesAdaptor(com.example.lenovo.trackapp.actv.AddPreRequestActivity.this, requestTyoesList);
                listTypes.setAdapter(adapto);
            }

            @Override
            public void onFailure(Call<ResMetaReqTypes> call, Throwable throwable) {

            }
        });
    }

   /* public void getPurposeList() {
        Singleton.getInstance().getApi().getPurposeList("").enqueue(new Callback<ResponseMeta>() {
            @Override
            public void onResponse(Call<ResponseMeta> call, Response<ResponseMeta> response) {

            }

            @Override
            public void onFailure(Call<ResponseMeta> call, Throwable t) {

            }
        });
    }*/


    AlertDialog alertDialog;
    MeetingsAdaptor adaptor;

    private void showMeetings() {

        adaptor = new MeetingsAdaptor(com.example.lenovo.trackapp.actv.AddPreRequestActivity.this, meetingList);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        // ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.meeting_popup, null);
        final ListView listPurpose = dialogView.findViewById(R.id.listPurpose);
        TextView title = dialogView.findViewById(R.id.title);
        //Button btnUpgrade = (Button) dialogView.findViewById(R.id.btnUpgrade);
        title.setText("Selected Created Meetings");
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        alertDialog.show();
        listPurpose.setAdapter(adaptor);

        listPurpose.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                MeetingModel obj = (MeetingModel) listPurpose.getAdapter().getItem(position);
                edtMeetings.setText(obj.getPurpose());
                alertDialog.dismiss();
            }
        });

    }


    private void showRequestType() {
        RequestTypesAdaptor adapto = new RequestTypesAdaptor(com.example.lenovo.trackapp.actv.AddPreRequestActivity.this, requestTyoesList);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        // ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.request_types, null);
        final ListView listPurpose = dialogView.findViewById(R.id.listPurpose);
        TextView title = dialogView.findViewById(R.id.title);
        title.setText("Select Request Type");
        //Button btnUpgrade = (Button) dialogView.findViewById(R.id.btnUpgrade);
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        alertDialog.show();
        listPurpose.setAdapter(adapto);

        listPurpose.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                RequestTypeModel obj = (RequestTypeModel) listPurpose.getAdapter().getItem(position);
                edtRequestType.setText(obj.getRequest_type());
                AppCompatCheckBox chk = view.findViewById(R.id.chkSelect);
              /*  if (chk.isSelected())
                    requestTyoesList.get(position).setSelected(true);*/
                alertDialog.dismiss();
            }
        });

    }


}
