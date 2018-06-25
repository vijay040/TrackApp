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
import com.mmcs.trackapp.util.Shprefrences;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by aphroecs on 8/12/2016.
 */
public class FragmentHome extends Fragment {


    Shprefrences sh;
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
