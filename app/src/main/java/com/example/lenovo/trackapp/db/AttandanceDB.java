package com.example.lenovo.trackapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Lenovo on 03-04-2018.
 */

public class AttandanceDB extends SQLiteOpenHelper {
    public AttandanceDB(Context context) {
        super(context, "attandance", null, 1);
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
        Cursor cursor=null;

        SQLiteDatabase db= this.getReadableDatabase();

        cursor=db.rawQuery("select * from history" , null);

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
