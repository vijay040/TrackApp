package com.example.lenovo.trackapp.actv;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.trackapp.R;
import com.example.lenovo.trackapp.adaptor.CurrencyAdaptor;
import com.example.lenovo.trackapp.adaptor.CustomerPopupAdaptor;
import com.example.lenovo.trackapp.adaptor.DepartmentAdaptor;
import com.example.lenovo.trackapp.adaptor.MeetingsAdaptor;
import com.example.lenovo.trackapp.adaptor.PurposePopupAdaptor;
import com.example.lenovo.trackapp.adaptor.RequestTypesAdaptor;
import com.example.lenovo.trackapp.model.CurrencyModel;
import com.example.lenovo.trackapp.model.CustomerDetails;
import com.example.lenovo.trackapp.model.CustomerModel;
import com.example.lenovo.trackapp.model.DepartmentModel;
import com.example.lenovo.trackapp.model.LoginModel;
import com.example.lenovo.trackapp.model.MeetingModel;
import com.example.lenovo.trackapp.model.PurposeModel;
import com.example.lenovo.trackapp.model.RequestTypeModel;
import com.example.lenovo.trackapp.model.ResMetaCurrency;
import com.example.lenovo.trackapp.model.ResMetaCustomer;
import com.example.lenovo.trackapp.model.ResMetaDepartment;
import com.example.lenovo.trackapp.model.ResMetaMeeting;
import com.example.lenovo.trackapp.model.ResMetaReqTypes;
import com.example.lenovo.trackapp.model.ResponseMeta;
import com.example.lenovo.trackapp.util.CurrencyFormatInputFilter;
import com.example.lenovo.trackapp.util.Shprefrences;
import com.example.lenovo.trackapp.util.Singleton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Lenovo on 22-05-2018.
 */

public class AddPreRequestActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    EditText edtMeetings, edtDescreption, edtAdvance, edtDepartment, edtCurrency;
    Button btnSubmit;
    ProgressBar progress;
    Shprefrences sh;
    ArrayList<MeetingModel> meetingList = new ArrayList<>();
    ArrayList<RequestTypeModel> requestTyoesList = new ArrayList<>();
    ArrayList<DepartmentModel> departmentList = new ArrayList<>();
    ArrayList<CurrencyModel> currencyList = new ArrayList<>();
    ListView listTypes;

    //SweetAlertDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_prerequest_activity);
        edtMeetings = findViewById(R.id.edtMeetings);
        edtDescreption = findViewById(R.id.edtDescreption);
        edtAdvance = findViewById(R.id.edtAdvance);
        btnSubmit = findViewById(R.id.btnSubmit);
        listTypes = findViewById(R.id.listTypes);
        edtDepartment = findViewById(R.id.edtDepartment);
        edtCurrency = findViewById(R.id.edtCurrency);
        progress = findViewById(R.id.progress);
        sh = new Shprefrences(this);
        progress.setVisibility(View.VISIBLE);

        getReqestTypes();
        getMeetingsList();
        getDepartmentList();
        getCurrencyList();
        //edtAdvance.setFilters(new InputFilter[] {new CurrencyFormatInputFilter()});
        edtMeetings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMeetings();
            }
        });

        edtDepartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDepartmentList();
            }
        });

        edtCurrency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCurrencyList();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitPost();
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

    public void getDepartmentList() {
        LoginModel model = sh.getLoginModel("LOGIN_MODEL");
        Singleton.getInstance().getApi().getDepartmentList(model.getId()).enqueue(new Callback<ResMetaDepartment>() {
            @Override
            public void onResponse(Call<ResMetaDepartment> call, Response<ResMetaDepartment> response) {
                departmentList = response.body().getResponse();
            }

            @Override
            public void onFailure(Call<ResMetaDepartment> call, Throwable t) {

            }
        });
    }

    private void getCurrencyList() {
        LoginModel model = sh.getLoginModel("LOGIN_MODEL");
        Singleton.getInstance().getApi().getCurrencyList(model.getUser_id()).enqueue(new Callback<ResMetaCurrency>() {
            @Override
            public void onResponse(Call<ResMetaCurrency> call, Response<ResMetaCurrency> response) {
                currencyList = response.body().getResponse();
            }

            @Override
            public void onFailure(Call<ResMetaCurrency> call, Throwable t) {

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
                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResMetaReqTypes> call, Throwable throwable) {
                progress.setVisibility(View.GONE);
            }
        });
    }


    AlertDialog alertDialog;
    MeetingsAdaptor adaptor;
    private int popupId = 0;

    private void showMeetings() {

        adaptor = new MeetingsAdaptor(com.example.lenovo.trackapp.actv.AddPreRequestActivity.this, meetingList);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        // ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.meeting_popup, null);
        final ListView listPurpose = dialogView.findViewById(R.id.listPurpose);

        TextView title = dialogView.findViewById(R.id.title);
        final SearchView editTextName = dialogView.findViewById(R.id.edt);
        editTextName.setQueryHint("Search Here");
        editTextName.setOnQueryTextListener(this);
        //Button btnUpgrade = (Button) dialogView.findViewById(R.id.btnUpgrade);
        title.setText("Selected Created Meetings");
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        popupId = 1;
        alertDialog.show();
        listPurpose.setAdapter(adaptor);

        listPurpose.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                MeetingModel obj = (MeetingModel) listPurpose.getAdapter().getItem(position);
                Log.e("selected**", "" + obj.getDescreption());
                edtMeetings.setText(obj.getDescreption());
                alertDialog.dismiss();
            }
        });

    }

    DepartmentAdaptor departmentAdaptor;

    private void showDepartmentList() {
        departmentAdaptor = new DepartmentAdaptor(AddPreRequestActivity.this, departmentList);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        // ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.meeting_popup, null);
        final ListView listPurpose = dialogView.findViewById(R.id.listPurpose);
        TextView title = dialogView.findViewById(R.id.title);
        final SearchView editTextName = dialogView.findViewById(R.id.edt);
        editTextName.setIconified(true);
        editTextName.setQueryHint("Search Here");
        editTextName.setOnQueryTextListener(this);
        title.setText("Select Department");
        //Button btnUpgrade = (Button) dialogView.findViewById(R.id.btnUpgrade);
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        popupId = 3;
        alertDialog.show();
        listPurpose.setAdapter(departmentAdaptor);

        listPurpose.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                DepartmentModel obj = (DepartmentModel) listPurpose.getAdapter().getItem(position);
                edtDepartment.setText(obj.getDepartment_name());
                alertDialog.dismiss();
            }
        });

    }
    CurrencyAdaptor currencyAdaptor;
    private void showCurrencyList() {
        currencyAdaptor = new CurrencyAdaptor(AddPreRequestActivity.this, currencyList);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        // ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.meeting_popup, null);
        final ListView listPurpose = dialogView.findViewById(R.id.listPurpose);
        final SearchView editTextName = dialogView.findViewById(R.id.edt);
        TextView title = dialogView.findViewById(R.id.title);
        editTextName.setQueryHint("Search Here");
        editTextName.setOnQueryTextListener(this);

        title.setText("Select Currency");
        //Button btnUpgrade = (Button) dialogView.findViewById(R.id.btnUpgrade);
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        popupId = 2;
        alertDialog.show();
        listPurpose.setAdapter(currencyAdaptor);

        listPurpose.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                CurrencyModel obj = (CurrencyModel) listPurpose.getAdapter().getItem(position);
                edtCurrency.setText(obj.getCurrency_name());
                alertDialog.dismiss();
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
                ArrayList<MeetingModel> newlist = new ArrayList<>();
                for (MeetingModel list : meetingList) {
                    String getName = list.getDescreption().toLowerCase();

                    if (getName.contains(s)) {
                        newlist.add(list);
                    }
                }
                adaptor.filter(newlist);
                break;
            case 2:
                ArrayList<CurrencyModel> newlist1 = new ArrayList<>();
                for (CurrencyModel list : currencyList) {
                    String getCurrency = list.getCurrency_name().toLowerCase();

                    if (getCurrency.contains(s)) {
                        newlist1.add(list);
                    }
                }
                currencyAdaptor.filter(newlist1);
                break;

            case 3:
                ArrayList<DepartmentModel> newlist2 = new ArrayList<>();
                for (DepartmentModel list : departmentList) {
                    String getDepartment = list.getDepartment_name().toLowerCase();

                    if (getDepartment.contains(s)) {
                        newlist2.add(list);
                    }
                }
                departmentAdaptor.filter(newlist2);
                break;
        }
        return true;
    }


    private void submitPost() {
        LoginModel model = sh.getLoginModel("LOGIN_MODEL");
        String userid = model.getUser_id();
        String adv = edtAdvance.getText().toString();
        String curr = edtCurrency.getText().toString();
        String dept = edtDepartment.getText().toString();
        String meetind_id = edtMeetings.getText().toString();
        String des = edtDescreption.getText().toString();
        if (userid.equals("")) {
            Toast.makeText(AddPreRequestActivity.this, "Your session is time out please login again", Toast.LENGTH_SHORT).show();
            return;
        } else if (adv.equals("")) {
            Toast.makeText(AddPreRequestActivity.this, "Enter Advance Amount", Toast.LENGTH_SHORT).show();
            return;
        } else if (curr.equals("")) {
            Toast.makeText(AddPreRequestActivity.this, "Select Currency", Toast.LENGTH_SHORT).show();
            return;
        } else if (dept.equals("")) {
            Toast.makeText(AddPreRequestActivity.this, "Select Department", Toast.LENGTH_SHORT).show();
            return;
        } else if (meetind_id.equals("")) {
            Toast.makeText(AddPreRequestActivity.this, "Select Meeting", Toast.LENGTH_SHORT).show();
            return;
        } else if (des.equals("")) {
            Toast.makeText(AddPreRequestActivity.this, "Enter Descreption", Toast.LENGTH_SHORT).show();
            return;
        }

        progress.setVisibility(View.VISIBLE);
        Singleton.getInstance().getApi().postPreRequest(userid, adv, curr, dept, meetind_id, des, requestTyoesList).enqueue(new Callback<ResMetaMeeting>() {
            @Override
            public void onResponse(Call<ResMetaMeeting> call, Response<ResMetaMeeting> response) {
                progress.setVisibility(View.GONE);
                Toast.makeText(AddPreRequestActivity.this, "Pre-Request Submited Successfully!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResMetaMeeting> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });
    }

}