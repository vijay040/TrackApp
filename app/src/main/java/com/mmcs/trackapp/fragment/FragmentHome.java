package com.mmcs.trackapp.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mmcs.trackapp.R;
import com.mmcs.trackapp.activity.AddCustomerActivity;
import com.mmcs.trackapp.activity.AttandanceActivity;
import com.mmcs.trackapp.activity.CreateMeetingActivity;
import com.mmcs.trackapp.activity.ExpenseActivity;
import com.mmcs.trackapp.activity.FeedbackActivity;
import com.mmcs.trackapp.activity.LandingActivity;
import com.mmcs.trackapp.activity.LoginActivity;
import com.mmcs.trackapp.activity.MyScheduleActivity;
import com.mmcs.trackapp.activity.PendingActivity;
import com.mmcs.trackapp.activity.SendMessageActivity;
import com.mmcs.trackapp.activity.SettingActivity;
import com.mmcs.trackapp.model.LoginModel;
import com.mmcs.trackapp.util.Shprefrences;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by aphroecs on 8/12/2016.
 */
public class FragmentHome extends Fragment {

    TextView addvisit, expenses, attandance, schedule, addcustomer, feedback, pending, message, setting;
    Shprefrences sh;
    String name;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

     /*   sh = new Shprefrences(getActivity());
        progressDialog = new Dialog(getActivity());
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setContentView(R.layout.progress_lay);
        progressDialog.setCancelable(false);*/
        // progressDialog.show();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_main, container, false);
        TextView txtWelcomeText = view.findViewById(R.id.txtWelcomeText);
        addvisit =  view.findViewById(R.id.btn_addvisit);
        expenses =  view.findViewById(R.id.btn_expense);
        attandance =  view.findViewById(R.id.btn_attendance);
        schedule = view.findViewById(R.id.btn_mysch);
        message =  view.findViewById(R.id.btn_message);
        feedback =  view.findViewById(R.id.btn_feedback);
        addcustomer =  view.findViewById(R.id.btn_addcustomer);
        pending =  view.findViewById(R.id.btn_notification);
        message =  view.findViewById(R.id.btn_message);
        setting =  view.findViewById(R.id.btn_setting);
        sh = new Shprefrences(getActivity());

        LoginModel model = sh.getLoginModel("LOGIN_MODEL");
        if (model != null)
            name = model.getDisplay_name();
        txtWelcomeText.setText("Hi " + name + "! B Tracker Welcomes You.");
        // getSupportActionBar().setTitle("B Tracker");
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),SettingActivity.class));
            }
        });
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SendMessageActivity.class);
                startActivity(intent);
            }
        });

        pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //sh.clearData();
                Intent intent = new Intent(getActivity(), PendingActivity.class);
                startActivity(intent);

            }
        });
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FeedbackActivity.class);
                startActivity(intent);
            }
        });
        addcustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddCustomerActivity.class);
                startActivity(intent);
            }
        });

        schedule.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getActivity(), MyScheduleActivity.class);
                startActivity(intent);
            }
        });
        attandance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(getActivity(), AttandanceActivity.class);
                startActivity(intent);
            }
        });
        addvisit.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(getActivity(), CreateMeetingActivity.class);
                startActivity(intent);
            }
        });
        expenses.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(getActivity(), ExpenseActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    public void onResume() {
        super.onResume();
    }


}
