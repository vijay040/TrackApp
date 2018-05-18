package com.example.lenovo.trackapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Lenovo on 03-04-2018.
 */

public class CreateMeetDB extends SQLiteOpenHelper {
    public CreateMeetDB(Context context) {
        super(context, "name", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table meeting(descreption text,customername text,place text,date text,time text,agenda text,contactperson text)");

    }
    public void  saveData(String des,String custnm,String pl,String dt,String tm,String agnda,String cntprsn ) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cn=new ContentValues();
        cn.put("descreption", des);
        cn.put("customername", custnm);
        cn.put("place", pl);
        cn.put("date", dt);
        cn.put("time", tm);
        cn.put("agenda", agnda);
        cn.put("contactperson", cntprsn);
        db.insert("meeting" , null , cn);

    }
    public Cursor fetchData()
    {
        Cursor cursor=null;

        SQLiteDatabase db= this.getReadableDatabase();

        cursor=db.rawQuery("select * from meeting" , null);

        if(cursor!=null)
        {
            cursor.moveToFirst();
        }

        return  cursor;
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
