package com.mmcs.trackapp.activity;

import android.content.Intent;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
                    Toast.makeText(LoginActivity.this, "Enter User Name", Toast.LENGTH_SHORT).show();
                } else if (pass.equals("")) {
                    Toast.makeText(LoginActivity.this, "Enter Your Password", Toast.LENGTH_SHORT).show();
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
                LoginModel model=response.body().getResponse();
                Log.e("","inside**************************************");
                if(model!=null)
                {
                    Log.e("","inside*****************************model!=null********");
                    if(model.getId()!=null)
                    {
                        sh.setLoginModel("LOGIN_MODEL", model);
                        sh.setBoolean("ISLOGIN",true);
                        startActivity(new Intent(LoginActivity.this, LandingActivity.class));
                        finish();
                    }
                    if(model.getCode()!=null) {
                        Toast.makeText(LoginActivity.this, ""+ model.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                        Toast.makeText(LoginActivity.this, "Please Try Again!", Toast.LENGTH_SHORT).show();
                }

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<LoginResMeta> call, Throwable t) {
                progress.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Please Try Again!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

}
