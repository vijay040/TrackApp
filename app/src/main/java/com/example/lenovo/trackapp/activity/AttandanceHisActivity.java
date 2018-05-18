package com.example.lenovo.trackapp.activity;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lenovo.trackapp.db.AttandanceDB;
import com.example.lenovo.trackapp.R;
import com.example.lenovo.trackapp.db.SignOutDB;

public class AttandanceHisActivity extends AppCompatActivity {
    AttandanceDB database=new AttandanceDB(this);
   SignOutDB database1=new SignOutDB(this);
    ListView list,listout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attandance_his);
        Cursor cursor= database.fetchData();

        if(!(cursor.moveToFirst())||cursor.getCount()==0) {
            Toast.makeText(AttandanceHisActivity.this, "there is no history to show", Toast.LENGTH_LONG).show();


        }





        else{


            ArrayAdapter<String> adptr = new ArrayAdapter<String>(AttandanceHisActivity.this,android.R.layout.simple_list_item_1);

            do
            {

                String L=cursor.getString(cursor.getColumnIndex("location"));

                String D=cursor.getString(cursor.getColumnIndex("cdate"));
                String T=cursor.getString(cursor.getColumnIndex("ctime"));
                adptr.add(L+"\n"+D+"\n"+T);






            }while (cursor.moveToNext());
            list=(ListView)findViewById(R.id.mobile_list);

            list.setAdapter(adptr);


        }
        //  textView.setText(stringBuffer.toString());


    }



}


