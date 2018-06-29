package com.mmcs.trackapp.activity;

import android.app.AlertDialog;
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
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.mmcs.trackapp.R;
import com.mmcs.trackapp.adaptor.PurposePopupAdaptor;
import com.mmcs.trackapp.model.PurposeModel;
import com.mmcs.trackapp.model.ResponseMeta;
import com.mmcs.trackapp.util.Singleton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendMessageActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
Button btnSend;
EditText edtUserName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);
        btnSend=findViewById(R.id.btnSend);
        edtUserName=findViewById(R.id.edtUserName);
        getCustomerList();
        edtUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomerPopup();
            }
        });

        back();
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    AlertDialog alertDialog;
    ArrayList<PurposeModel> purposeList = new ArrayList<>();
    PurposePopupAdaptor purposePopupAdaptor;
    private int popupId = 0;

    private void showCustomerPopup() {
       /* PurposeModel m=new PurposeModel();
        m.setPurpose("Business Meeting in Noida");
        m.setId("0");

        purposeList.add(m);*/
        purposePopupAdaptor = new PurposePopupAdaptor(SendMessageActivity.this, purposeList);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        // ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.meeting_popup, null);
        final ListView listPurpose = dialogView.findViewById(R.id.listPurpose);
        //Button btnUpgrade = (Button) dialogView.findViewById(R.id.btnUpgrade);
        final SearchView editTextName = dialogView.findViewById(R.id.edt);
        editTextName.setQueryHint("Search Here");
        editTextName.setOnQueryTextListener(this);
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        alertDialog.show();
        popupId = 1;
        listPurpose.setAdapter(purposePopupAdaptor);

        listPurpose.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                PurposeModel obj = (PurposeModel) listPurpose.getAdapter().getItem(position);
                edtUserName.setText(obj.getPurpose());
                alertDialog.dismiss();
            }
        });

    }
    public void getCustomerList() {
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
        return false;
    }
}
