package com.tmp.micah.regloginmodule.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

/**
 * Created by Micah on 22/11/2016.
 */
public class SessionManager {
    //LogCat TAG
    private static String TAG = SessionManager.class.getSimpleName();

    //Shared Preferences
    SharedPreferences sharedPref;

    Editor editor;
    Context context;

    //Shared Preference Mode
    int  PRIVATE_MODE = 0;

    //Shared Preferences File Name
    private static final String PREF_NAME = "RegLoginModule";

    //Shared Preferences Key Value
    private static final  String KEY_IS_LOGGEDIN = "isLoggedIn";

    public SessionManager(Context context){
        this.context = context;
        sharedPref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPref.edit();
    }

    public void setLogin(boolean isLoggedIn){
        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);

        //commit changes
        editor.commit();

        Log.d(TAG, "User login session modified!");
    }

    public boolean isLoggedIn(){
        return sharedPref.getBoolean(KEY_IS_LOGGEDIN, false);
    }
}
