package com.mmcs.trackapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.mmcs.trackapp.R;
import com.mmcs.trackapp.model.ResAttandance;
import com.mmcs.trackapp.util.Shprefrences;
import com.mmcs.trackapp.util.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SplashActivity extends AppCompatActivity {

    Shprefrences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sh = new Shprefrences(SplashActivity.this);
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.e("MYTAG", "This is your Firebase token" + token);
        Handler h = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                return false;
            }
        });

        h.postDelayed(new Runnable() {
            @Override
            public void run() {

                boolean isLogin = sh.getBoolean(getString(R.string.is_login), false);
                if (isLogin)
                    startActivity(new Intent(SplashActivity.this, DrawerActivity.class));
                else
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();

            }
        }, 3000);

    }


    public void test()
    {
        Singleton.getInstance().getApi().test(getString(R.string.test_email),getString(R.string.test_password)).enqueue(new Callback<ResAttandance>() {
            @Override
            public void onResponse(Call<ResAttandance> call, Response<ResAttandance> response) {

            }

            @Override
            public void onFailure(Call<ResAttandance> call, Throwable t) {

            }
        });
    }

}
