package com.example.lenovo.trackapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Lenovo on 03-04-2018.
 */

public class SignOutDB extends SQLiteOpenHelper {
    public SignOutDB(Context context) {
        super(context, "signout", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table history(location text,cdate text,ctime text)");



    }

    public void saveData(String l ,String dt,String tm)
    {
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues cn=new ContentValues();
        cn.put("location" , l);

        cn.put("cdate" , dt);
        cn.put("ctime" , tm);


        db.insert("history" , null , cn);


    }
    public Cursor fetchData()
    {
        Cursor cursor1=null;

        SQLiteDatabase db= this.getReadableDatabase();

        cursor1=db.rawQuery("select * from history" , null);

        if(cursor1!=null)
        {
            cursor1.moveToFirst();
        }

        return  cursor1;
    }



    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
