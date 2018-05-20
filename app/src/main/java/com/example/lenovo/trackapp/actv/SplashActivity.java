package com.example.lenovo.trackapp.actv;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.example.lenovo.trackapp.R;
import com.example.lenovo.trackapp.activity.MainActivity;
import com.example.lenovo.trackapp.model.LoginModel;
import com.example.lenovo.trackapp.util.Shprefrences;


public class SplashActivity extends AppCompatActivity {

    Shprefrences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sh = new Shprefrences(SplashActivity.this);
        Handler h = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                return false;
            }
        });

        h.postDelayed(new Runnable() {
            @Override
            public void run() {

                boolean isLogin = sh.getBoolean("ISLOGIN", false);
                if (isLogin)
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                else
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();

            }
        }, 3000);

    }

}
