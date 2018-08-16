package com.mmcs.trackapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mmcs.trackapp.R;
import com.mmcs.trackapp.activity.AddCustomerActivity;
import com.mmcs.trackapp.activity.AttandanceActivity;
import com.mmcs.trackapp.activity.CreateMeetingActivity;
import com.mmcs.trackapp.activity.ExpenseActivity;
import com.mmcs.trackapp.activity.FeedbackActivity;
import com.mmcs.trackapp.activity.FeedbackListActivity;
import com.mmcs.trackapp.activity.MyScheduleActivity;
import com.mmcs.trackapp.activity.PendingActivity;
import com.mmcs.trackapp.activity.MessageActivity;
import com.mmcs.trackapp.activity.ReminderdetailActivity;
import com.mmcs.trackapp.activity.SalesCheckingActivity;
import com.mmcs.trackapp.activity.SettingActivity;
import com.mmcs.trackapp.model.FeedbackResMeta;
import com.mmcs.trackapp.model.LoginModel;
import com.mmcs.trackapp.model.MeetingModel;
import com.mmcs.trackapp.model.ResMetaMeeting;
import com.mmcs.trackapp.util.Shprefrences;
import com.mmcs.trackapp.util.Singleton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by aphroecs on 8/12/2016.
 */
public class FragmentHome extends Fragment {

    TextView addvisit, expenses, attandance, schedule, addcustomer, feedback, pending, message, setting,sales_checking;
    Shprefrences sh;
    String name;
    ImageView animatedClockView;
    private AnimatedVectorDrawableCompat animatedClock;
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

        View view = inflater.inflate(R.layout.fragment_home, container, false);

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
        txtWelcomeText = view.findViewById(R.id.txtWelcomeText);
         animatedClockView = view.findViewById(R.id.img_clock);
        sales_checking=view.findViewById(R.id.btn_salescheckin);
       // Animation shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
       // animatedClockView.startAnimation(shake);

        Animation animation = new AlphaAnimation(1, 0);
        animation.setDuration(1000);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.REVERSE);
        animatedClockView.startAnimation(animation);

        animatedClockView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Meeting Is Running!", Toast.LENGTH_SHORT).show();
            }
        });

        // Creating though compat library
      //  animatedClock = AnimatedVectorDrawableCompat.create(getActivity(), R.drawable.avd_clock_rotate);
       // animatedClockView.setImageDrawable(animatedClock);

        sh = new Shprefrences(getActivity());

        LoginModel model = sh.getLoginModel(getString(R.string.login_model));
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
                Intent intent = new Intent(getActivity(), MessageActivity.class);
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
                Intent intent = new Intent(getActivity(), FeedbackListActivity.class);
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
        sales_checking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), SalesCheckingActivity.class);
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
        if(animatedClockView!=null)
            getRunningMeetings();
    }

    private void getRunningMeetings()
    {
        LoginModel model = sh.getLoginModel(getString(R.string.login_model));
        if(model==null)
            return;
        Singleton.getInstance().getApi().getRunningMeetings(model.getId()).enqueue(new Callback<ResMetaMeeting>() {
            @Override
            public void onResponse(Call<ResMetaMeeting> call, Response<ResMetaMeeting> response) {
                ArrayList<MeetingModel>list=response.body().getResponse();
                if(list.get(0).getMeeting_count()==null)
                    list.get(0).setMeeting_count("0");
                if(Integer.parseInt(list.get(0).getMeeting_count())>0)
                {
                    animatedClockView.setVisibility(View.VISIBLE);
                }
                else {
                    animatedClockView.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<ResMetaMeeting> call, Throwable t) {

            }
        });
    }



}
