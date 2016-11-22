package com.tmp.micah.regloginmodule.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by Micah on 22/11/2016.
 */
public class SQLiteHandler extends SQLiteOpenHelper{
    private static final String TAG = SQLiteHandler.class.getSimpleName();

    //Database Version
    private static final int DB_VERSION = 1;
    //Database Name
    private static final String DB_NAME = "android_api";
    //Login Table Name
    private static final String TABLE_NAME = "user";
    //Login Table Column Names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_UID= "uid";
    private static final String KEY_CREATED_AT = "created_at";



    public SQLiteHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    //Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        //SQL statement for creating table
        String CREATE_LOGIN_TABLE = "CREATE TABLE "+TABLE_NAME+" ("
                +KEY_ID+" INTEGER PRIMARY KEY,"
                +KEY_NAME+" TEXT,"
                +KEY_EMAIL+" TEXT UNIQUE,"
                +KEY_UID+" TEXT,"
                +KEY_CREATED_AT+" TEXT" + ")";

        db.execSQL(CREATE_LOGIN_TABLE);
        Log.d(TAG, "Database tables created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop older table if exists
        db.execSQL("DROP IF EXISTS" +TABLE_NAME);

        //Create Table Again
        onCreate(db);
    }

    //Storing user details in database
    public void addUser(String name, String email, String uid, String created_at){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_EMAIL, email);
        values.put(KEY_UID, uid);
        values.put(KEY_CREATED_AT, created_at);

        //Inserting rows
        long id = db.insert(TABLE_NAME, null, values);
        db.close();

        Log.d(TAG, "New user inserted into sqlite db:" + id);
    }

    //Method used to get user data from database
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        //SQL select statement
        String selectQuery = "SELECT * FROM "+ TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        //Move cursor to first row
        if(cursor.getCount()>0){
            user.put("name", cursor.getString(1));
            user.put("email", cursor.getString(2));
            user.put("uid", cursor.getString(3));
            user.put("created_at", cursor.getString(4));
        }
        cursor.close();
        db.close();

        Log.d(TAG, "Fetching suer from sqlite db: "+user.toString());
        return user;
    }

    //Method used to delete users
    public void deleteUsers(){
        SQLiteDatabase db = this.getWritableDatabase();
        //delete all rows
        db.delete(TABLE_NAME,null, null);
        db.close();

        Log.d(TAG, "Deleted all user information from sqlite db");
    }
}
