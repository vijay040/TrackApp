package com.example.lenovo.trackapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Lenovo on 04-04-2018.
 */

public class Category extends SQLiteOpenHelper {
    public Category(Context context) {
        super(context, "category", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table addcategory(category text )");

    }
    public void saveData(String Cate )
    {
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues cn=new ContentValues();
        cn.put("category" , Cate);




        db.insert("addcategory" , null , cn);


    }
    public Cursor fetchData()
    {
        Cursor cursor=null;

        SQLiteDatabase db= this.getReadableDatabase();

        cursor=db.rawQuery("select * from addcategory" , null);

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
