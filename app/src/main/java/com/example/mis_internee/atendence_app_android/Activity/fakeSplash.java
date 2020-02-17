package com.example.mis_internee.atendence_app_android.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;

import android.telephony.TelephonyManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mis_internee.atendence_app_android.Model.User;
import com.example.mis_internee.atendence_app_android.R;
import com.example.mis_internee.atendence_app_android.Utils.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class fakeSplash extends AppCompatActivity {

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
    private String DESIG_CODE,APP_FLAG, EMP_CODE, SHIFT_CODE, DEP_ID, DESIG_TYPE, DESIG_ID, DEP_CODE, UNIT_ID, UNIT_CODE, IMEI, IS_MANAGER;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fake_splash);
//        getSupportActionBar().hide();
        final User user = new User(fakeSplash.this);
        final String USERNAME=getIntent().getExtras().getString("username");
        String emp_id = user.getEmp_id();
        showUrl = "http://scorpio.sgroup.pk:8085/atendence/hr_leave.php" + "?emp_id=" + emp_id;
        EmployeUrl = "http://scorpio.sgroup.pk:8085/atendence/select.php" + "?emp_id=" + emp_id;
        Timer timer = new Timer();




        requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                showUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response.toString());
//                Toast.makeText(fakeSplash.this, response.toString()+"", Toast.LENGTH_SHORT).show();
                try {
                    JSONArray Leaves = response.getJSONArray("Result");
                    for (int i = 0; i < Leaves.length(); i++) {
                        JSONObject leave = Leaves.getJSONObject(i);

                        leave_desc = leave.getString("LEAVE_DESC");
                        L_Desc.add(leave_desc);
                        leave_id = leave.getString("LEAVE_ID");
                      L_ID.add(leave_id);
                        leave_code = leave.getString("LEAVE_CODE");
                         L_code.add(leave_code);
                      String leaves = leave.getString("LEAVES");
                        Utility.LEAVE_BAL.add(leaves.trim());

//                        result.append(leave_desc + " " + leave_id + " " + leave_code + " \n");
                    }
//                    result.append("===\n");

                } catch (JSONException e) {
                    ProgressDialog pDialog = new ProgressDialog(fakeSplash.this);
                    pDialog.setMessage("Api error...!!!");
                    pDialog.show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.append(error.getMessage());

            }
        });
        requestQueue.add(jsonObjectRequest);


        getAlldata();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (user.getName() != "") {
                    Intent intent = new Intent(fakeSplash.this, MainActivity.class);
                    Bundle bdl= new Bundle();
                    intent.putExtra("emp_id", user.getEmp_id());
                    intent.putExtra("username",USERNAME);
                    intent.putExtra("EMP_CODE",EMP_CODE);
                    intent.putExtra("DESIG_CODE",DESIG_CODE);
                    intent.putExtra("SHIFT_CODE",SHIFT_CODE);
                    intent.putExtra("DEP_ID",DEP_ID);
                    intent.putExtra("APP_FLAG",APP_FLAG);
                    intent.putExtra("DESIG_TYPE",DESIG_TYPE);
                    intent.putExtra("DESIG_ID",DESIG_ID);
                    intent.putExtra("DEP_CODE",DEP_CODE);
                    intent.putExtra("UNIT_ID",UNIT_ID);
                    intent.putExtra("UNIT_CODE",UNIT_CODE);
                    intent.putExtra("CREATED_BY",IMEI);
                    intent.putExtra("IS_MANAGER",IS_MANAGER);
                    bdl.putStringArrayList("LEAVE_ID",L_ID);
                    bdl.putStringArrayList("LEAVE_CODE",L_code);
                    bdl.putStringArrayList("LEAVE_DESC",L_Desc);
                    intent.putExtras(bdl);

                    startActivity(intent);
                   fakeSplash.super.finish();
                } else {
                    Intent intent = new Intent(fakeSplash.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 2000);




    }

    public void getAlldata() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                EmployeUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response.toString());

                try {
                    JSONArray employee = response.getJSONArray("Result");
                    for (int i = 0; i < employee.length(); i++) {
                        JSONObject empobj = employee.getJSONObject(i);
                        EMP_CODE= empobj.getString("EMP_CODE");
                        //  APP_FLAG= empobj.getString("APP_FLAG");
                        DEP_CODE = empobj.getString("DEP_CODE");
                        DESIG_CODE = empobj.getString("DESIG_CODE");
                        SHIFT_CODE = empobj.getString("SHIFT_CODE");
                        DESIG_TYPE = empobj.getString("DESIG_TYPE");
                        DEP_ID = empobj.getString("DEP_ID");
                        DESIG_ID = empobj.getString("DESIG_ID");
                        UNIT_ID=empobj.getString("UNIT_ID");
                        UNIT_CODE=empobj.getString("UNIT_CODE");
                        IS_MANAGER=empobj.getString("IS_MANAGER");
                        String Arrivl = empobj.getString("ACHKIN_TIME");
                        Arrival_time.add(Arrivl);
                        String status = empobj.getString("ATTND_FLAG");
                        Status.add(status);
                        String offTime = empobj.getString("ACHKOUT_TIME");
                        Off_time.add(offTime);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.append(error.getMessage());

            }
        });
        requestQueue.add(jsonObjectRequest);


    }



}