package com.example.lenovo.trackapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.lenovo.trackapp.R;

public class MainActivity extends AppCompatActivity {
    Button addvisit,expenses,attandance,schedule,addcustomer,feedback,logout,message,setting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addvisit=(Button)findViewById(R.id.btn_addvisit);
        expenses=(Button)findViewById(R.id.btn_expense);
        attandance=(Button)findViewById(R.id.btn_attendance);
        schedule=(Button)findViewById(R.id.btn_mysch);
        message=(Button)findViewById(R.id.btn_message);
        feedback=(Button)findViewById(R.id.btn_feedback);
        addcustomer=(Button)findViewById(R.id.btn_addcustomer);
        logout=(Button)findViewById(R.id.btn_logout);
        message=(Button)findViewById(R.id.btn_message);
        setting=(Button)findViewById(R.id.btn_setting);
getSupportActionBar().setTitle("Business Tracker");
setting.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Toast.makeText(MainActivity.this,"This is setting",Toast.LENGTH_SHORT).show();
    }
});
message.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent=new Intent(MainActivity.this,SendMessageActivity.Messages.class);
        startActivity(intent);
    }
});

logout.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Toast.makeText(MainActivity.this,"This is Logout Page",Toast.LENGTH_SHORT).show();
    }
});




        feedback.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent= new Intent(MainActivity.this,FeedbackActivity.class);
        startActivity(intent);
                         }
                             });
addcustomer.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent=new Intent(MainActivity.this,AddCustomerActivity.class);
        startActivity(intent);
      }
     });

        schedule.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent=new Intent( MainActivity.this,MyScheduleActivity.class);
                startActivity(intent);

            }
        });
        attandance.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent=new Intent( MainActivity.this,SignInActivity.Attandance.class);
                startActivity(intent);

            }
        });
        addvisit.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent=new Intent( MainActivity.this,CteateMeetingActivity.class);
                startActivity(intent);
            }
        });
        expenses.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent=new Intent( MainActivity.this,ExpenseActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation, menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        int	itemid=item.getItemId();
      if(itemid==R.id.nav_addmeeting) {

           Intent intent = new Intent(MainActivity.this, CteateMeetingActivity.class);
           startActivity(intent);
      }

       else if(itemid==R.id.nav_mysch) {
           Intent intent=new Intent( MainActivity.this,MyScheduleActivity.class);
           startActivity(intent);
       }
       else if(itemid==R.id.nav_feedback) {
           Intent intent= new Intent(MainActivity.this,FeedbackActivity.class);
           startActivity(intent);
       }
       else if(itemid==R.id.nav_attandance) {
           Intent intent=new Intent( MainActivity.this,SignInActivity.Attandance.class);
           startActivity(intent);
       }
       else if(itemid==R.id.nav_customer) {
           Intent intent=new Intent(MainActivity.this,AddCustomerActivity.class);
           startActivity(intent);
       }
       else if(itemid==R.id.nav_expense) {
           Intent intent=new Intent( MainActivity.this,ExpenseActivity.class);
           startActivity(intent);
       }
       else if(itemid==R.id.nav_setting) {
           Toast.makeText(this, "setting", Toast.LENGTH_SHORT).show();
       }
       else if(itemid==R.id.nav_message){
          Toast.makeText(this, "this is your message", Toast.LENGTH_SHORT).show();
      }
      else if(itemid==R.id.nav_logout){
          Toast.makeText(this, "this is Logout", Toast.LENGTH_SHORT).show();
      }
        return super.onOptionsItemSelected(item);
    }
}
