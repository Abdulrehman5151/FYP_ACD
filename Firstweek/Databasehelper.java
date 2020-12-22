package com.example.advocatecasediary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Databasehelper extends SQLiteOpenHelper {
    public static  final  String DATABASE_NAME= "Sign.db";
    public static  final  String TABLE_NAME ="Registration";
    public static  final  String COL_1= "ID";
    public static  final  String COL_2= "Cnic";
    public static  final  String COL_3= "Name";
    public static  final  String COL_4= "Email";
    public static  final  String COL_5= "Password";
    public static  final  String COL_6= "Phone";
    public static  final  String COL_7= "Address";
    public Databasehelper (Context context){ super (context,DATABASE_NAME,null,1);}
    @Override

    public void onCreate (SQLiteDatabase db)
    {
        db.execSQL( "CREATE TABLE " +TABLE_NAME+"(ID INTEGER PRIMARY KEY, auto INCREAMENT, Cnic TEXT, Name TEXT, Email TEXT, Password TEXT , Phone TEXT, Address TEXT)");
    }
     @Override
    public void onUpgrade(SQLiteDatabase db , int oldVersion , int newVersion)
     {
         db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
         onCreate(db);

     }

}
