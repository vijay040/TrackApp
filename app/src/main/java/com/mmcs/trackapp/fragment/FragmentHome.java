package com.mmcs.trackapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.mmcs.trackapp.activity.DrawerActivity;
import com.mmcs.trackapp.adaptor.HomeRecyclerAdaptor;
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

    Shprefrences sh;
    RecyclerView rvHome;
    String name;
    ImageView animatedClockView;
    private AnimatedVectorDrawableCompat animatedClock;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sh = new Shprefrences(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        //TextView txtWelcomeText = view.findViewById(R.id.txtWelcomeText);
        rvHome = view.findViewById(R.id.rvItems);
        rvHome.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        rvHome.setAdapter(DrawerActivity.homeAdaptor);
        TextView txtWelcomeText = view.findViewById(R.id.txtWelcomeText);
        animatedClockView = view.findViewById(R.id.img_clock);

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

        LoginModel model = sh.getLoginModel(getString(R.string.login_model));
        if (model != null)
            name = model.getDisplay_name();
         txtWelcomeText.setText("Hi " + name + "! B Tracker Welcomes You.");

        Log.e("homeAdaptor*****","**********************"+DrawerActivity.homeAdaptor);
        return view;
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
     //   rvHome.setAdapter(DrawerActivity.homeAdaptor);
        if (animatedClockView != null)
            getRunningMeetings();

    }

    private void getRunningMeetings() {
        LoginModel model = sh.getLoginModel(getString(R.string.login_model));
        if (model == null)
            return;
        Singleton.getInstance().getApi().getRunningMeetings(model.getId()).enqueue(new Callback<ResMetaMeeting>() {
            @Override
            public void onResponse(Call<ResMetaMeeting> call, Response<ResMetaMeeting> response) {
                ArrayList<MeetingModel> list = response.body().getResponse();
                if (list.get(0).getMeeting_count() == null)
                    list.get(0).setMeeting_count("0");
                if (Integer.parseInt(list.get(0).getMeeting_count()) > 0) {
                    animatedClockView.setVisibility(View.VISIBLE);
                } else {
                    animatedClockView.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<ResMetaMeeting> call, Throwable t) {

            }
        });
    }


}
