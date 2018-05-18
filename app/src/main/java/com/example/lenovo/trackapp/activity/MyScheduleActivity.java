package com.example.lenovo.trackapp.activity;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lenovo.trackapp.db.CreateMeetDB;
import com.example.lenovo.trackapp.R;

public class MyScheduleActivity extends AppCompatActivity {

    ListView list;
    Button clear;
   CreateMeetDB database=new CreateMeetDB(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_schedule);


        Cursor cursor= database.fetchData();
        if(!(cursor.moveToFirst())||cursor.getCount()==0){
            Toast.makeText(MyScheduleActivity.this, "there is no schedule to show", Toast.LENGTH_LONG).show();




        }
        else{
            ArrayAdapter<String> adptr = new ArrayAdapter<String>(MyScheduleActivity.this,android.R.layout.simple_list_item_1);


            do
            {

                String D=cursor.getString(cursor.getColumnIndex("descreption"));
                String C=cursor.getString(cursor.getColumnIndex("customername"));
                String P=cursor.getString(cursor.getColumnIndex("place"));
                String Dt=cursor.getString(cursor.getColumnIndex("date"));
                String T=cursor.getString(cursor.getColumnIndex("time"));
                String A=cursor.getString(cursor.getColumnIndex("agenda"));
                String CN=cursor.getString(cursor.getColumnIndex("contactperson"));

                adptr.add("Descreption:"+D+"\n"+"Customer  Name:"+C+"\n"+"Place Name:"+P+"\n"+"Date:"+Dt+"\n"+"Time:"+T+"\n"+"Agenda:"+A+"\n"+"Contact Person:"+CN);









            }while (cursor.moveToNext());
            list=(ListView)findViewById(R.id.mobile_list);

            list.setAdapter(adptr);

        }


    }




}

