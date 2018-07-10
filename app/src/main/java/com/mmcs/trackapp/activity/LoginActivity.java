package com.mmcs.trackapp.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mmcs.trackapp.R;
import com.mmcs.trackapp.model.LoginModel;
import com.mmcs.trackapp.model.LoginResMeta;
import com.mmcs.trackapp.util.Shprefrences;
import com.mmcs.trackapp.util.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText username,password;
    Button btn_signin;
    CheckBox remember;
    TextView forgotpassword;
    Shprefrences sh;
    ProgressBar progress;
    RelativeLayout lay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        lay=findViewById(R.id.lay);
        lay.setVisibility(View.GONE);
      //  getSupportActionBar().hide();
        username = findViewById(R.id.edt_usernsme);
        password = findViewById(R.id.edt_password);
      /*  forgotpassword = findViewById(R.id.txt_forgot);
        remember = findViewById(R.id.checkBox1);*/
        btn_signin=findViewById(R.id.btn_signin);
        progress=findViewById(R.id.progress);
        sh=new Shprefrences(this);

        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_name = username.getText().toString();
                String pass = password.getText().toString();
                if (user_name.equals("")) {
                    Toast.makeText(LoginActivity.this, getString(R.string.enter_user_name), Toast.LENGTH_SHORT).show();
                } else if (pass.equals("")) {
                    Toast.makeText(LoginActivity.this, getString(R.string.enter_your_password), Toast.LENGTH_SHORT).show();
                } else {
                    progress.setVisibility(View.VISIBLE);
                    login(user_name,pass);
                }
            }
        });
    }

    private void login(String email,String pass)
    {
        Singleton.getInstance().getApi().login(email,pass,"","").enqueue(new Callback<LoginResMeta>() {
            @Override
            public void onResponse(Call<LoginResMeta> call, Response<LoginResMeta> response) {
                    if(response.body().getCode()!=null && response.body().getCode().equalsIgnoreCase("200"))
                    {  LoginModel model=response.body().getResponse().get(0);
                        sh.setLoginModel(getString(R.string.login_model), model);
                        sh.setBoolean("ISLOGIN",true);
                        startActivity(new Intent(LoginActivity.this, DrawerActivity.class));
                        finish();
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Invalid userid and password!", Toast.LENGTH_SHORT).show();
                    }


                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<LoginResMeta> call, Throwable t) {
                progress.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(),  getString(R.string.please_try_again),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

}
