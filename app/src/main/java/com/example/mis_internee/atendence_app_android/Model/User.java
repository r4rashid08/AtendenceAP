package com.example.mis_internee.atendence_app_android.Model;

/**
 * Created by MIS-Internee on 29-Dec-17.
 */

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by MIS-Internee on 29-Nov-17.
 */

public class User {
    Context context;
    SharedPreferences sharedPreferences;
    private String name;
    private String emp_id;
    private String ATD_DATE;
    private String EMP_CODE;
    private String USERNAME;
    private String IS_MANAGER;

    public String getIS_MANAGER() {
        IS_MANAGER=sharedPreferences.getString("IS_MANAGER","");
        return IS_MANAGER;
    }

    public void setIS_MANAGER(String IS_MANAGER) {
        this.IS_MANAGER = IS_MANAGER;
        sharedPreferences.edit().putString("IS_MANAGER",IS_MANAGER).commit();
    }

    public String getUSERNAME() {
        USERNAME=sharedPreferences.getString("USERNAME","");
        return USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
        sharedPreferences.edit().putString("USERNAME",USERNAME).commit();
    }









    public String getEMP_CODE() {
        EMP_CODE=sharedPreferences.getString("EMP_CODE","");
        return EMP_CODE;
    }

    public void setEMP_CODE(String EMP_CODE) {
        this.EMP_CODE = EMP_CODE;
        sharedPreferences.edit().putString("EMP_CODE",EMP_CODE).commit();
    }



    public String getATD_DATE() {
        ATD_DATE=sharedPreferences.getString("ATD_DATE","");
        return ATD_DATE;
    }

    public void setATD_DATE(String ATD_DATE) {
        this.ATD_DATE = ATD_DATE;
        sharedPreferences.edit().putString("ATD_DATE",ATD_DATE).commit();
    }
    public String getEmp_id() {
        emp_id=sharedPreferences.getString("UserDataa","");
        return emp_id;
    }

    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
        sharedPreferences.edit().putString("UserDataa",emp_id).commit();
    }




    public void removeUser(){
        sharedPreferences.edit().clear().commit();
    }

    public String getName() {
        name=sharedPreferences.getString("UserData","");
        return name;
    }

    public void setName(String name) {
        this.name = name;
        sharedPreferences.edit().putString("UserData",name).commit();
    }



    public User(Context context)
    {
        this.context=context;
        sharedPreferences=context.getSharedPreferences("UserData",Context.MODE_PRIVATE);

    }
}
