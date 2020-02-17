package com.example.mis_internee.atendence_app_android.Activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.telephony.TelephonyManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mis_internee.atendence_app_android.Model.User;
import com.example.mis_internee.atendence_app_android.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.HashMap;
import java.util.Map;


public class Splash_Activity extends AppCompatActivity {

    EditText firstname, lastname, age;
    Button insert, show;
    RequestQueue requestQueue;
    TelephonyManager tel;
    String showUrl;
    String EmployeUrl;
    TextView result;
    String leave_id, leave_code, leave_desc;
    ArrayList<String> L_ID = new ArrayList<String>();
    ArrayList<String> L_code = new ArrayList<String>();
    ArrayList<String> L_Desc = new ArrayList<String>();
    ArrayList<String> Arrival_time = new ArrayList<String>();
    ArrayList<String> Off_time = new ArrayList<String>();
    ArrayList<String> Status = new ArrayList<String>();

    String DESIG_CODE,APP_FLAG, EMP_CODE, SHIFT_CODE, DEP_ID, DESIG_TYPE, DESIG_ID, DEP_CODE, UNIT_ID, UNIT_CODE, IMEI, MANAGER_CODE,IS_MANAGER,DUE_DAYS;
    String token ="TESTING";
    private String emp_id;
    private Timer timer;
    private User user;
    String LeaveData;
   String version_no;
    Handler handler;
    ProgressDialog getdata;
    private String leave_bal;
    private ArrayList<String> L_bal= new ArrayList<String>();;
    private TextView version;
    private PackageManager managers;
    private PackageInfo infor;
    private String pakageInfo,versionCode,versionName;

    String insertToken="http://scorpio.sgroup.pk:8085/atendence/regToken.php";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//        getSupportActionBar().hide();
       user = new User(Splash_Activity.this);
       version=(TextView)findViewById(R.id.version);
        managers = this.getPackageManager();
        infor = null;
        try {
            infor = managers.getPackageInfo(this.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        pakageInfo=infor.packageName.toString();
        versionCode= String.valueOf(infor.versionCode);
        versionName=infor.versionName.toString();

        version.setText("Version: "+versionCode);

        emp_id = user.getEmp_id();






        if(emp_id!=null){
             Intent i = new Intent(getApplicationContext(),fakeSplash.class);
              i.putExtra("username",user.getName());
              startActivity(i);
              finish();
           // Toast.makeText(this, "empId"+emp_id, Toast.LENGTH_SHORT).show();

        }else {
               Intent i = new Intent(getApplicationContext(),LoginActivity.class);
               startActivity(i);
               finish();
          //  Toast.makeText(this, "empID is null ", Toast.LENGTH_SHORT).show();


        }



      



    }




    public static boolean hasPermissions(Context context, String... permissions){

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M && context!=null && permissions!=null){
            for(String permission: permissions){
                if(ActivityCompat.checkSelfPermission(context, permission)!=PackageManager.PERMISSION_GRANTED){
                    return  false;
                }
            }
        }
        return true;
    }



}
