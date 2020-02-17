package com.example.mis_internee.atendence_app_android.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


public class PrefManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "Attendance";

    // All Shared Preferences Keys

    private static final String KEY_MONTHyEAR = "image";




    @SuppressLint("CommitPrefEdits")
    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }








    public void setMonthYear(String key_month,String monthYear) {
        editor.putString(key_month, monthYear);
        editor.commit();
    }

    public String getMonthYear(String keyMonth) {
        return pref.getString(keyMonth, "0");
    }




    public void clearSession() {
        editor.clear();
        editor.commit();
    }


}
