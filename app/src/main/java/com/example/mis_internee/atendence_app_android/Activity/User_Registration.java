package com.example.mis_internee.atendence_app_android.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.mis_internee.atendence_app_android.R;

public class User_Registration extends AppCompatActivity {
    TelephonyManager tel;
    TextView imei;
    EditText Emp_id, Password, Cpaswd;
    Button bRegis;
    private ProgressDialog progressdialog;
    private int conter = 0;
    private String EMP_ID, PASSWD, IMEI_NO;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);
        Typeface fontAwesomeFont = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");
        TextView fontAwesomeAndroidIcon = (TextView) findViewById(R.id.font_awesome_android_icon);
        TextView key = (TextView) findViewById(R.id.font_awesome_android_key);
        TextView Ckey = (TextView) findViewById(R.id.conPass);
        fontAwesomeAndroidIcon.setTypeface(fontAwesomeFont);
        key.setTypeface(fontAwesomeFont);
        Ckey.setTypeface(fontAwesomeFont);
        tel = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        Emp_id = (EditText) findViewById(R.id.emp_id);
        Password = (EditText) findViewById(R.id.password);
        Cpaswd = (EditText) findViewById(R.id.con_paswd);
        bRegis = (Button) findViewById(R.id.btnReg);
        imei = (TextView) findViewById(R.id.textView2);
        tel = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        IMEI_NO = tel.getDeviceId().toString();

//        }
        imei.setText(IMEI_NO);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//
//            return;
//        }
//        imei.setText(tel.getDeviceId().toString());

        bRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conter++;
                if (conter == 1) {
                    progressdialog = new ProgressDialog(User_Registration.this);
                    progressdialog.setMessage("Please Wait Loading data....");
                    progressdialog.show();
                    progressdialog.setCancelable(false);
                }
                EMP_ID = Emp_id.getText().toString();
                PASSWD = Password.getText().toString();
//                if (ActivityCompat.checkSelfPermission(User_Registration.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//                    return;
//                }
//                IMEI_NO = tel.getDeviceId().toString();
                if (Emp_id.getText().toString().equals("")) {
                    Toast.makeText(getApplication(), "Enter Employee id", Toast.LENGTH_LONG).show();
                } else if (Password.getText().toString().equals("")) {
                    Toast.makeText(getApplication(), "Enter Password ", Toast.LENGTH_LONG).show();
                } else if (!Cpaswd.getText().toString().equals(PASSWD)) {
                    Toast.makeText(getApplication(), "Enter Valid Password", Toast.LENGTH_LONG).show();
                } else {
                    InsertData();
                }


            }
        });
    }

    public void InsertData() {

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("success")) {
                    progressdialog.hide();
                    Toast.makeText(getApplication(), "Successfully Registered", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(User_Registration.this, LoginActivity.class);
                    User_Registration.this.startActivity(intent);
                } else {
                    progressdialog.hide();
                    Toast.makeText(getApplication(), "Already Account Exist...!!!", Toast.LENGTH_LONG).show();
                }

            }
        };

        RegisterRequest registerRequest = new RegisterRequest(EMP_ID, PASSWD, IMEI_NO, responseListener);
        RequestQueue queue = Volley.newRequestQueue(User_Registration.this);
        queue.add(registerRequest);
    }
}